package com.xpense.services.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCard {
    private String name;
    private Double amount;
    private Integer percentage;
    private Boolean profit;
}
