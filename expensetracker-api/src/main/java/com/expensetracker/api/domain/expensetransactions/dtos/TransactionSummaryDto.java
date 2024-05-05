package com.expensetracker.api.domain.expensetransactions.dtos;

import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class TransactionSummaryDto {
    private TransactionType type;
    private BigDecimal amount;
}
