package com.expensetracker.api.domain.expensetransactions.dtos;

import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import com.expensetracker.api.domain.expensetransactions.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ExpenseTransactionDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private TransactionType type;
    private Category category;
    private String description;
    private LocalDateTime createdAt;
}
