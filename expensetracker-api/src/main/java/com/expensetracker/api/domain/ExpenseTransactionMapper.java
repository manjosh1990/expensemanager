package com.expensetracker.api.domain;

import org.springframework.stereotype.Component;

@Component
public class ExpenseTransactionMapper {

    public ExpenseTransactionDTO toDTO(ExpenseTransaction expenseTransaction){
        ExpenseTransactionDTO dto = new ExpenseTransactionDTO();
        dto.setId(expenseTransaction.getId());
        dto.setCategory(expenseTransaction.getCategory());
        dto.setAmount(expenseTransaction.getAmount());
        dto.setDescription(expenseTransaction.getDescription());
        dto.setTransactionDate(expenseTransaction.getTransactionDate());
        dto.setCreatedAt(expenseTransaction.getCreatedAt());
        dto.setType(expenseTransaction.getType());
        return dto;
    }
}
