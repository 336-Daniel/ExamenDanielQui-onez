package com.uti.svcorders.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishResponse {

    private Long id;

    private String name;

    private String description;

    private String category;

    private Double price;

    private Boolean available;
}
