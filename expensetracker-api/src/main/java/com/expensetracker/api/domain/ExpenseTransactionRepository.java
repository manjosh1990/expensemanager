package com.expensetracker.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction,Long> {
    @Query("SELECT new com.expensetracker.api.domain.ExpenseTransactionDTO(e.id,e.amount,e.transactionDate,e.type,e.category,e.description,e.createdAt) from ExpenseTransaction e")
    Page<ExpenseTransactionDTO> findExpenseTransactions(Pageable pageable);
}
