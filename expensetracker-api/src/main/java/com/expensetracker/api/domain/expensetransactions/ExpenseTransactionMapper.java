package com.expensetracker.api.domain.expensetransactions;

import com.expensetracker.api.domain.expensetransactions.entity.ExpenseTransaction;
import com.expensetracker.api.domain.expensetransactions.dtos.ExpenseTransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class ExpenseTransactionMapper {

    public ExpenseTransactionDTO toDTO(ExpenseTransaction expenseTransaction){
        return new ExpenseTransactionDTO(expenseTransaction.getId(),
                expenseTransaction.getAmount(),
                expenseTransaction.getTransactionDate(),
                expenseTransaction.getType(),
                expenseTransaction.getCategory(),
                expenseTransaction.getDescription(),
                expenseTransaction.getCreatedAt()
                );
    }
}
