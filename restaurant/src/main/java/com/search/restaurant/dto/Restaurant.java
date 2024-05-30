package com.search.restaurant.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    private String name;
    private Integer customerRating;
    private Integer distance;
    private Integer price;
    private Integer cuisineId;
}
