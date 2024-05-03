package com.expensetracker.api.domain.expensetransactions;


import com.expensetracker.api.domain.expensetransactions.dtos.CreateTransactionRequest;
import com.expensetracker.api.domain.expensetransactions.dtos.ExpenseTransactionDTO;
import com.expensetracker.api.domain.expensetransactions.dtos.TransactionsDTO;
import com.expensetracker.api.domain.expensetransactions.entity.Category;
import com.expensetracker.api.domain.expensetransactions.entity.ExpenseTransaction;
import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseTransactionRepository repository;
    private final ExpenseTransactionMapper mapper;
    @Transactional(readOnly = true)
    public TransactionsDTO getTransactions(Integer page){
        int pageNo = page <1 ?0 :page-1;
        Pageable pageable = PageRequest.of(pageNo,10, Sort.Direction.DESC,"createdAt");
        Page<ExpenseTransactionDTO> transactionsDTO = repository.findExpenseTransactions(pageable);
        return new TransactionsDTO(transactionsDTO);
    }
    @Transactional(readOnly = true)
    public TransactionsDTO searchTransactionsByCategory(String category, Integer page) {
        int pageNo = page <1 ?0 :page-1;
        Pageable pageable = PageRequest.of(pageNo,10, Sort.Direction.DESC,"transactionDate");
        Page<ExpenseTransactionDTO> transactionsDTO = repository.searchTransactionsByCategory(Category.valueOf(category),pageable);
        return new TransactionsDTO(transactionsDTO);
    }
    @Transactional(readOnly = true)
    public TransactionsDTO searchTransactionsByType(String type, Integer page) {
        int pageNo = page <1 ?0 :page-1;
        Pageable pageable = PageRequest.of(pageNo,10, Sort.Direction.DESC,"transactionDate");
        Page<ExpenseTransactionDTO> transactionsDTO = repository.findExpenseTransactionsByType(TransactionType.valueOf(type),pageable);
        return new TransactionsDTO(transactionsDTO);
    }

    public ExpenseTransactionDTO createExpenseTransaction(CreateTransactionRequest request) {
        ExpenseTransaction expenseTransaction = new ExpenseTransaction(
                null,
                request.getAmount()
                ,request.getTransactionDate(),
                TransactionType.valueOf(request.getType()),
                Category.valueOf(request.getCategory()),
                request.getDescription(),
                LocalDateTime.now(),null);//need to implement user specific
        ExpenseTransaction newTransaction = repository.save(expenseTransaction);
       return mapper.toDTO(newTransaction);
    }

    public List<Category> getAllCategories() {
        return Arrays.asList(Category.values());
    }

    public List<TransactionType> getAllTransactionTypes() {
        return Arrays.asList(TransactionType.values());
    }

    public void deleteExpenseTransaction(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public BigDecimal getSumByTransactionType(String type) {
        switch (type){
            case "EXPENSE":
                return repository.getTotalAmountByTypeForCurrentMonth(TransactionType.EXPENSE);
            case "INCOME":
                return repository.getTotalAmountByTypeForCurrentMonth(TransactionType.INCOME);
            case "INVESTMENT":
                return repository.getTotalAmountByTypeForCurrentMonth(TransactionType.INVESTMENT);
            default:
               return new BigDecimal(0);
        }
    }
}
