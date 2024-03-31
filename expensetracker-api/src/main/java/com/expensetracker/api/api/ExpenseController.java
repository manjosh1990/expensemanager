package com.expensetracker.api.api;

import com.expensetracker.api.domain.ExpenseService;
import com.expensetracker.api.domain.ExpenseTransaction;
import com.expensetracker.api.domain.TransactionsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expense/transactions")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService service;

    @GetMapping
    public TransactionsDTO getTransactions(@RequestParam(name = "page",defaultValue = "1") Integer page){
        return service.getTransactions(page);
    }
}
