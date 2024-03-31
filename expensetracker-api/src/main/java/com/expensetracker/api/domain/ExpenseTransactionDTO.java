package com.expensetracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTransactionDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private ExpenseTransaction.TransactionType type;
    private ExpenseTransaction.Category category;
    private String description;
    private LocalDateTime createdAt;
}
