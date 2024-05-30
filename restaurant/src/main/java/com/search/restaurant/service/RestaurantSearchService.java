package com.search.restaurant.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RestaurantSearchService {


    public Map<Integer, String> findCuisine(Map<Integer, String> cuisineMap, String requestedCuisine){

        return cuisineMap
                .entrySet()
                .stream()
                .filter( cuisineMapEntry ->
                        cuisineMapEntry.getValue().toLowerCase().contains(requestedCuisine.toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> {throw new RuntimeException();}, HashMap::new));


    }

}

