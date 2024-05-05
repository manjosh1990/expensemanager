package com.expensetracker.api.domain.expensetransactions.dtos;

import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class ExpenseTransactionChartDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private TransactionType type;
}
