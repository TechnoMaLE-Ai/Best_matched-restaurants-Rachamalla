package com.search.restaurant.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestoSearchResponse {
    private List<RestoSearchRequest> searchList;
    private String errorCode;
    private String errorDescription;
}
