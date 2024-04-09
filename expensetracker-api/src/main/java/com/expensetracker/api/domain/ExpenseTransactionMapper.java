package com.expensetracker.api.domain;

import org.springframework.stereotype.Component;

@Component
public class ExpenseTransactionMapper {

    public ExpenseTransactionDTO toDTO(ExpenseTransaction expenseTransaction){
        ExpenseTransactionDTO dto = new ExpenseTransactionDTO(expenseTransaction.getId(),
                expenseTransaction.getAmount(),
                expenseTransaction.getTransactionDate(),
                expenseTransaction.getType(),
                expenseTransaction.getCategory(),
                expenseTransaction.getDescription(),
                expenseTransaction.getCreatedAt()
                );
        return dto;
    }
}
