package com.expensetracker.api.domain.expensetransactions;

import com.expensetracker.api.domain.expensetransactions.entity.Category;
import com.expensetracker.api.domain.expensetransactions.entity.ExpenseTransaction;
import com.expensetracker.api.domain.expensetransactions.dtos.ExpenseTransactionDTO;
import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction,Long> {
    @Query("SELECT new com.expensetracker.api.domain.expensetransactions.dtos.ExpenseTransactionDTO(e.id,e.amount,e.transactionDate,e.type,e.category,e.description,e.createdAt) from ExpenseTransaction e")
    Page<ExpenseTransactionDTO> findExpenseTransactions(Pageable pageable);

    @Query("""
            SELECT new com.expensetracker.api.domain.expensetransactions.dtos.ExpenseTransactionDTO(e.id,e.amount,e.transactionDate,e.type,e.category,e.description,e.createdAt)
            from ExpenseTransaction e where e.category=:category
            """)
    Page<ExpenseTransactionDTO> searchTransactionsByCategory(Category category, Pageable pageable);

    Page<ExpenseTransactionDTO> findExpenseTransactionsByType(TransactionType type, Pageable pageable);
    @Query("""
            SELECT sum(e.amount) from ExpenseTransaction e where MONTH(e.transactionDate)= MONTH(CURRENT_DATE) AND YEAR(e.transactionDate) = YEAR(CURRENT_DATE) and e.type=:type
    """)
    BigDecimal getTotalAmountByTypeForCurrentMonth(TransactionType type);
}
