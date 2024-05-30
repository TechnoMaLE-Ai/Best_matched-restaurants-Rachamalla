package com.search.restaurant.dao;

import com.search.restaurant.dto.Restaurant;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantSearchDao {

    public List<Restaurant> readRestaurantCSV(){
        List<Restaurant> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\racha\\Downloads\\Best-matched-restaurant-Rachamalla\\restaurants.csv"))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                if(index > 0){
                    String[] values = line.split(",");
                    records.add(Restaurant.builder().name(values[0])
                            .customerRating(Integer.valueOf(values[1]))
                            .distance(Integer.valueOf(values[2]))
                            .price(Integer.valueOf(values[3]))
                            .cuisineId(Integer.valueOf(values[4])).
                            build());
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    public Map<Integer, String> readCuisinesCSV(){
        Map<Integer, String> cuisinesMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\racha\\Downloads\\Best-matched-restaurant-Rachamalla\\cuisines.csv"))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                if(index > 0){
                    String[] values = line.split(",");
                    cuisinesMap.put(Integer.valueOf(values[0]), values[1]);

                }
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cuisinesMap;
    }
}
