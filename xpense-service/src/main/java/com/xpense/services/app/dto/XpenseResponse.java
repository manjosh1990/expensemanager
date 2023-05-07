package com.xpense.services.app.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Data
public class XpenseResponse {
    private String name;
    private BigDecimal expense;
    private String percentage;
    private boolean hasIncreased;
}
