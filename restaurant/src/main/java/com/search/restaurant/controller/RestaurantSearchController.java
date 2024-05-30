package com.search.restaurant.controller;

import com.search.restaurant.dao.RestaurantSearchDao;
import com.search.restaurant.dto.Errors;
import com.search.restaurant.dto.Restaurant;
import com.search.restaurant.dto.RestoSearchRequest;
import com.search.restaurant.dto.RestoSearchResponse;
import com.search.restaurant.service.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class RestaurantSearchController {

    @Autowired
    RestaurantSearchDao restaurantSearchDao;
    @Autowired
    RestaurantSearchService restaurantSearchService;
    private List<Restaurant> restaurants = new ArrayList<>();
    private Map<Integer, String> cuisineMap = new HashMap<>();
    private Errors error = null;
    private List<Restaurant> finalListOfRestaurants = new ArrayList<>();

    @GetMapping("/search")
    public RestoSearchResponse findBestMatchedRestaurant(@RequestBody RestoSearchRequest restoSearchRequest){
        error = new Errors();
        validateInputValues(restoSearchRequest);

        if(error.getErrorCode() == null){
            // Extracting the Restaurant and Cuisine from the CSV file.
            // This can be done at the start also so that we don't need to load it everytime the services comes based on the requirement.
            extractRestaurantsAndCuisine();
            filterNameSpecificRestaurant(restoSearchRequest.getName());
            filterCustomerRatingRestaurant(restoSearchRequest.getRating());
            filterDistantRestaurant(restoSearchRequest.getDistance());
            filterPriceRestaurant(restoSearchRequest.getPrice());
            filterCuisineSpecificRestaurant(restoSearchRequest.getCuisine());

            if(restaurants.size() == 0){
                error.setErrorCode("104");
                error.setErrorDescription("No Match found");
            } else {
                restaurants = restaurants
                        .stream()
                        .sorted((p1,p2) -> p1.getDistance().compareTo(p2.getDistance()))
                        .sorted((p1,p2) -> p2.getCustomerRating().compareTo(p1.getCustomerRating()))
                        .sorted((p1,p2) -> p1.getPrice().compareTo(p2.getPrice()))
                        .collect(Collectors.toList());
            }

            if(restaurants.size() > 5){
                restaurants = restaurants.stream().limit(5).collect(Collectors.toList());
            }

            finalListOfRestaurants = restaurants;
        }

        return generateResponse(finalListOfRestaurants);
    }

    private Errors validateInputValues(RestoSearchRequest restoSearchRequest) {
        if(restoSearchRequest.getRating() != null
                && (restoSearchRequest.getRating() > 5 || restoSearchRequest.getRating() < 0)){
            error.setErrorCode("101");
            error.setErrorDescription("Rating value is invalid");
        }
        if(restoSearchRequest.getDistance() != null
                && (restoSearchRequest.getDistance() > 20 || restoSearchRequest.getDistance() < 0)){
            error.setErrorCode("102");
            error.setErrorDescription("Distance value is invalid");
        }
        if(restoSearchRequest.getPrice() != null
                && restoSearchRequest.getPrice() < 0){
            error.setErrorCode("103");
            error.setErrorDescription("Price value is invalid");
        }

        return error;
    }

    private void extractRestaurantsAndCuisine(){
        restaurants = restaurantSearchDao.readRestaurantCSV();
        cuisineMap = restaurantSearchDao.readCuisinesCSV();
    }

    public RestoSearchResponse findExactMatch(RestoSearchRequest restoSearchRequest){
        return null;
    }

    private void filterCustomerRatingRestaurant(Integer rating){
        if(rating != null && rating > 0 && rating <= 5){
            restaurants = restaurants.stream().filter(restaurant -> restaurant.getCustomerRating() >= rating).collect(Collectors.toList());
        }
    }

    private void filterDistantRestaurant(Integer distance){
        restaurants = distance != null && distance > 0 ? restaurants.stream().filter(restaurant -> restaurant.getDistance() <= distance).collect(Collectors.toList()) : restaurants;
    }

    private void filterPriceRestaurant(Integer price){
        restaurants = price != null && price > 0 ? restaurants.stream().filter(restaurant -> restaurant.getPrice() <= price).collect(Collectors.toList()) : restaurants;
    }

    private void filterCuisineSpecificRestaurant(String cuisine){
        if(cuisine != null && !cuisine.isBlank()){
            Map<Integer, String> matchedCuisine = restaurantSearchService.findCuisine(cuisineMap, cuisine);
            restaurants = restaurants
                    .stream()
                    .filter(restaurant -> matchedCuisine.keySet()
                            .contains(restaurant.getCuisineId()))
                    .collect(Collectors.toList());
        }
    }

    private void filterNameSpecificRestaurant(String name){
        if(name != null && !name.isBlank()){
            restaurants = restaurants.stream().filter(restaurant -> restaurant.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        }
    }

    private void filterBestMatchedNameRestaurant(String name){
        if(name != null && !name.isBlank()){
            List<Restaurant> bestRestaurantsNameMatch = restaurants.stream().filter(restaurant -> restaurant.getName().startsWith(name)).collect(Collectors.toList());
            if(bestRestaurantsNameMatch.size() > 0){
                restaurants = bestRestaurantsNameMatch;
            }
            restaurants = bestRestaurantsNameMatch;
        }
    }


    private RestoSearchResponse generateResponse(List<Restaurant> finalRestaurantList){
        RestoSearchResponse resp = new RestoSearchResponse();
        if(error.getErrorCode() != null){
            resp.setErrorCode(error.getErrorCode());
            resp.setErrorDescription(error.getErrorDescription());
        } else {
            List<RestoSearchRequest> restaurantResponseDetails = new ArrayList<>();
            finalRestaurantList.stream().forEach(cuisineRestaurant -> {
                restaurantResponseDetails.add(RestoSearchRequest
                        .builder()
                        .name(cuisineRestaurant.getName())
                        .rating(cuisineRestaurant.getCustomerRating())
                        .distance(cuisineRestaurant.getDistance())
                        .price(cuisineRestaurant.getPrice())
                        .cuisine(cuisineMap.get(cuisineRestaurant.getCuisineId()))
                        .build());
            });
            resp.setSearchList(restaurantResponseDetails);
        }

        return resp;
    }
}

