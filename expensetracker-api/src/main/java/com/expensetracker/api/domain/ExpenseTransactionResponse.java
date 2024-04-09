package com.expensetracker.api.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ExpenseTransactionResponse {
    private Long id;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String type;
    private String category;
    private String description;
    private LocalDateTime createdAt;
    public static ExpenseTransactionResponse from(ExpenseTransactionDTO dto) {
        return new ExpenseTransactionResponse(
                dto.getId(),
                dto.getAmount(),
                dto.getTransactionDate(),
                dto.getType().name(),
                dto.getCategory().name(),
                dto.getDescription(),
                dto.getCreatedAt()
        );
    }
}
