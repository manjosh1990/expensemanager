package com.expensetracker.api;


import com.expensetracker.api.domain.Category;
import com.expensetracker.api.domain.ExpenseTransaction;
import com.expensetracker.api.domain.ExpenseTransactionRepository;
import com.expensetracker.api.domain.TransactionType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///expenseTest"
})
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ExpenseTransactionRepository repository;
    private List<ExpenseTransaction> transactions;

    @BeforeEach
    void setUp(){
        repository.deleteAllInBatch();
        transactions = new ArrayList<>();
        transactions.add(new ExpenseTransaction(null,new BigDecimal("1000.00"), LocalDate.now(), TransactionType.EXPENSE, Category.ENTERTAINMENT,"Movies", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("1300.00"),LocalDate.now(), TransactionType.EXPENSE, Category.FOOD,"Food", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("1200.00"),LocalDate.now(), TransactionType.EXPENSE, Category.TRANSPORTATION,"Fuel", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("1100.00"),LocalDate.now(), TransactionType.EXPENSE, Category.OTHER,"Credit Card", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("400.00"),LocalDate.now(), TransactionType.EXPENSE, Category.ENTERTAINMENT,"Prime", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("500.00"),LocalDate.now(), TransactionType.EXPENSE, Category.RENT,"OLA", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("600.00"),LocalDate.now(), TransactionType.EXPENSE, Category.FOOD,"Swiggy", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("200.00"),LocalDate.now(), TransactionType.EXPENSE, Category.TRANSPORTATION,"Car fuel", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("4000.00"),LocalDate.now(), TransactionType.EXPENSE, Category.FOOD,"Zomato", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null,new BigDecimal("120.00"),LocalDate.now(), TransactionType.EXPENSE, Category.FOOD,"Groceries", LocalDateTime.now()));

        repository.saveAll(transactions);
    }

    @ParameterizedTest
    @CsvSource({
        "1,10,1,1,false,false,true,true",
        "2,10,1,2,false,true,false,true"
    })
    void shouldGetExpenseTransaction(int pageNo,int totalElements,int totalPages, int currentPage,
    boolean hasNext,boolean hasPrevious, boolean isFirst, boolean isLast) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/expense/transactions?page="+pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)));
    }
}
