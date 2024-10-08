package com.expensetracker.api.api;

import com.expensetracker.api.domain.expensetransactions.ExpenseService;
import com.expensetracker.api.domain.expensetransactions.dtos.CreateTransactionRequest;
import com.expensetracker.api.domain.expensetransactions.dtos.DashboardStats;
import com.expensetracker.api.domain.expensetransactions.dtos.ExpenseTransactionDTO;
import com.expensetracker.api.domain.expensetransactions.dtos.TransactionsDTO;
import com.expensetracker.api.domain.expensetransactions.entity.Category;
import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense/transactions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping
    public TransactionsDTO getTransactionsByCategory(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(name = "category", defaultValue = "") String category,
                                                     @RequestParam(name = "type", defaultValue = "") String type,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if ((category == null || category.trim().isEmpty()) && (type == null || type.trim().isEmpty())) {
            return expenseService.getTransactions(page,pageSize);
        }
        if (type != null && !type.trim().isEmpty()) {
            return expenseService.searchTransactionsByType(type, page,pageSize);
        }
        return expenseService.searchTransactionsByCategory(category, page,pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseTransactionDTO createExpenseTransaction(@RequestBody @Valid CreateTransactionRequest request) {
        return expenseService.createExpenseTransaction(request);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategory() {
        List<Category> categories = expenseService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getTransactionType() {
        List<TransactionType> types = expenseService.getAllTransactionTypes();
        return ResponseEntity.ok(types);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteExpenseTransaction(@PathVariable Long id) {
        expenseService.deleteExpenseTransaction(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/sum/{type}")
    public ResponseEntity<?> getSumByType(@PathVariable String type) {
        return ResponseEntity.ok(expenseService.getSumByTransactionType(type));
    }

    @GetMapping("/stats")
    public DashboardStats getCurrentMonthStats() {
        return expenseService.getCurrentMonthStats();
    }
}
