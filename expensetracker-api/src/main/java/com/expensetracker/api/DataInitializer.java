package com.expensetracker.api;

import com.expensetracker.api.domain.ExpenseTransactionRepository;
import com.expensetracker.api.domain.ExpenseTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ExpenseTransactionRepository repository;
    @Override
    public void run(String... args) throws Exception {
        /*repository.save(new ExpenseTransaction(null,new BigDecimal("1000.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.ENTERTAINMENT,"Movies", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("1300.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.FOOD,"Food", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("1200.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.TRANSPORTATION,"Fuel", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("1100.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.OTHER,"Credit Card", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("400.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.ENTERTAINMENT,"Prime", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("500.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.RENT,"OLA", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("600.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.FOOD,"Swiggy", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("200.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.TRANSPORTATION,"Car fuel", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("4000.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.FOOD,"Zomato", LocalDateTime.now()));
        repository.save(new ExpenseTransaction(null,new BigDecimal("120.00"),LocalDate.now(), ExpenseTransaction.TransactionType.EXPENSE, ExpenseTransaction.Category.FOOD,"Groceries", LocalDateTime.now()));*/
    }
}
