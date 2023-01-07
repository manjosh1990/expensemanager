package com.xpense.services.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NetWorthResponse {
    Integer id;
    Double balance;
    Double totalSpent;
}
