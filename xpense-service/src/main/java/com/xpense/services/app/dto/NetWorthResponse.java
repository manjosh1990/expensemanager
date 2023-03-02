package com.xpense.services.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NetWorthResponse {
    BigDecimal balance;
    BigDecimal totalSpent;
}
