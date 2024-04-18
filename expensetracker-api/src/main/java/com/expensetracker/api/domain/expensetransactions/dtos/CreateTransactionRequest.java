package com.expensetracker.api.domain.expensetransactions.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateTransactionRequest {
    @NotNull(message = "amount should not be empty")
    private BigDecimal amount;
    @NotNull(message = "transaction Date should not be empty")
    private LocalDate transactionDate;
    @NotEmpty(message = "transaction type should not be empty")
    private String type;
    @NotEmpty(message = "category should not be empty")
    private String category;
    private String description;
}
