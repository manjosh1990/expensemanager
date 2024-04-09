package com.expensetracker.api.api;

import com.expensetracker.api.domain.CreateTransactionRequest;
import com.expensetracker.api.domain.ExpenseService;
import com.expensetracker.api.domain.ExpenseTransactionDTO;
import com.expensetracker.api.domain.TransactionsDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense/transactions")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping
    public TransactionsDTO getTransactionsByCategory(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "category", defaultValue = "") String category, @RequestParam(name = "type", defaultValue = "") String type) {
        if ((category == null || category.trim().length() == 0) && (type == null || type.trim().length() == 0)) {
            return expenseService.getTransactions(page);
        }
        if (type != null && !type.trim().isEmpty()) {
            return expenseService.searchTransactionsByType(type, page);
        }
        return expenseService.searchTransactionsByCategory(category, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseTransactionDTO createExpenseTransaction(@RequestBody @Valid CreateTransactionRequest request) {

        return expenseService.createExpenseTransaction(request);
    }

}
