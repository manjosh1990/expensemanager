package com.expensetracker.api;


import com.expensetracker.api.domain.expensetransactions.ExpenseTransactionRepository;
import com.expensetracker.api.domain.expensetransactions.entity.Category;
import com.expensetracker.api.domain.expensetransactions.entity.ExpenseTransaction;
import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @BeforeEach
    void setUp() {
        repository.deleteAllInBatch();
        List<ExpenseTransaction> transactions = new ArrayList<>();
        transactions.add(new ExpenseTransaction(null, new BigDecimal("1000.00"), LocalDate.now(), TransactionType.EXPENSE, Category.ENTERTAINMENT, "Movies", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("1300.00"), LocalDate.now(), TransactionType.EXPENSE, Category.FOOD, "Food", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("1200.00"), LocalDate.now(), TransactionType.EXPENSE, Category.TRANSPORTATION, "Fuel", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("1100.00"), LocalDate.now(), TransactionType.EXPENSE, Category.OTHER, "Credit Card", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("400.00"), LocalDate.now(), TransactionType.EXPENSE, Category.ENTERTAINMENT, "Prime", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("500.00"), LocalDate.now(), TransactionType.EXPENSE, Category.RENT, "OLA", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("600.00"), LocalDate.now(), TransactionType.EXPENSE, Category.FOOD, "Swiggy", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("200.00"), LocalDate.now(), TransactionType.EXPENSE, Category.TRANSPORTATION, "Car fuel", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("4000.00"), LocalDate.now(), TransactionType.EXPENSE, Category.FOOD, "Zomato", LocalDateTime.now()));
        transactions.add(new ExpenseTransaction(null, new BigDecimal("120.00"), LocalDate.now(), TransactionType.EXPENSE, Category.FOOD, "Groceries", LocalDateTime.now()));

        repository.saveAll(transactions);
    }

    @ParameterizedTest
    @CsvSource({
            "1,10,1,1,false,false,true,true",
            "2,10,1,2,false,true,false,true"
    })
    void shouldGetExpenseTransaction(int pageNo, int totalElements, int totalPages, int currentPage,
                                     boolean hasNext, boolean hasPrevious, boolean isFirst, boolean isLast) throws Exception {
        mvc.perform(get("/api/expense/transactions?page=" + pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)));
    }

    @Test
    void shouldCreateExpenseTransactionSuccessfully() throws Exception {
        this.mvc.perform(
                        post("/api/expense/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "amount": 12000.00,
                                            "transactionDate": "17/03/2024",
                                            "type": "INVESTMENT",
                                            "category": "MUTUAL_FUNDS",
                                            "description": "Scripbox SIP"
                                        }
                                        """)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.amount", is(12000.00)))
                .andExpect(jsonPath("$.type", is("INVESTMENT")));
    }


    @Test
    void shouldFailToCreateExpenseTransactionWhenAmountIsNotPresent() throws Exception {
        this.mvc.perform(
                        post("/api/expense/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                                            "transactionDate": "17/03/2024",
                                                            "type": "INVESTMENT",
                                                            "category": "MUTUAL_FUNDS",
                                                            "description": "Scripbox SIP"
                                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", is("application/problem+json")))
                .andExpect(jsonPath("$.type", is("https://zalando.github.io/problem/constraint-violation")))
                .andExpect(jsonPath("$.title", is("Constraint Violation")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", is("amount")))
                .andExpect(jsonPath("$.violations[0].message", is("amount should not be empty")))
                .andReturn();
    }

    @Test
    void shouldGetSumByTypeForCurrentMonth() throws Exception {
        this.mvc.perform(
                get("/api/expense/transactions/sum/EXPENSE")
        ).andExpect(status().isOk()).andExpect(result -> {
            String res = result.getResponse().getContentAsString();
            assertEquals(res, "10420.00");

        });
    }
}
