package com.expensetracker.api.api;

import com.expensetracker.api.domain.ExpenseService;
import com.expensetracker.api.domain.TransactionsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense/transactions")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping
    public TransactionsDTO getTransactionsByCategory(@RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "category",defaultValue = "") String category,@RequestParam(name = "type",defaultValue = "") String type){
        if((category == null || category.trim().length()==0) && (type == null || type.trim().length()==0)){
            return expenseService.getTransactions(page);
        }
        if(type != null && !type.trim().isEmpty()){
            return expenseService.searchTransactionsByType(type,page);
        }
        return expenseService.searchTransactionsByCategory(category,page);
    }

}
