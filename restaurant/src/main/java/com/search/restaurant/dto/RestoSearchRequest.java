package com.search.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RestoSearchRequest {
    private String name;
    private Integer rating;
    private Integer distance;
    private Integer price;
    private String cuisine;
}