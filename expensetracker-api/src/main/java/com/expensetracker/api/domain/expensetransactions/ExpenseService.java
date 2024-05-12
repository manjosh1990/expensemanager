package com.expensetracker.api.domain.expensetransactions;


import com.expensetracker.api.domain.expensetransactions.dtos.*;
import com.expensetracker.api.domain.expensetransactions.entity.Category;
import com.expensetracker.api.domain.expensetransactions.entity.ExpenseTransaction;
import com.expensetracker.api.domain.expensetransactions.entity.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {
    private final ExpenseTransactionRepository repository;
    private final ExpenseTransactionMapper mapper;

    @Transactional(readOnly = true)
    public TransactionsDTO getTransactions(Integer page, Integer pageSize) {
        int pageNo = page < 1 ? 0 : page - 1;
        int pageSizeValue = pageSize < 1 ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageNo, pageSizeValue, Sort.Direction.DESC, "transactionDate");
        Page<ExpenseTransactionDTO> transactionsDTO = repository.findExpenseTransactions(pageable);
        return new TransactionsDTO(transactionsDTO);
    }

    @Transactional(readOnly = true)
    public TransactionsDTO searchTransactionsByCategory(String category, Integer page,Integer pageSize) {
        int pageNo = page < 1 ? 0 : page - 1;
        int pageSizeValue = pageSize < 1 ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageNo, pageSizeValue, Sort.Direction.DESC, "transactionDate");
        Page<ExpenseTransactionDTO> transactionsDTO = repository.searchTransactionsByCategory(Category.valueOf(category), pageable);
        return new TransactionsDTO(transactionsDTO);
    }

    @Transactional(readOnly = true)
    public TransactionsDTO searchTransactionsByType(String type, Integer page,Integer pageSize) {
        int pageNo = page < 1 ? 0 : page - 1;
        int pageSizeValue = pageSize < 1 ? 10 : pageSize;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "transactionDate");
        Page<ExpenseTransactionDTO> transactionsDTO = repository.findExpenseTransactionsByType(TransactionType.valueOf(type), pageable);
        return new TransactionsDTO(transactionsDTO);
    }

    public ExpenseTransactionDTO createExpenseTransaction(CreateTransactionRequest request) {
        ExpenseTransaction expenseTransaction = new ExpenseTransaction(
                null,
                request.getAmount()
                , request.getTransactionDate(),
                TransactionType.valueOf(request.getType()),
                Category.valueOf(request.getCategory()),
                request.getDescription(),
                LocalDateTime.now(), null);//need to implement user specific
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
        Optional<BigDecimal> result = null;
        switch (type) {
            case "EXPENSE":
                result = repository.getTotalAmountByTypeForCurrentMonth(TransactionType.EXPENSE);
                break;
            case "INCOME":
                result = repository.getTotalAmountByTypeForCurrentMonth(TransactionType.INCOME);
                break;
            case "INVESTMENT":
                result = repository.getTotalAmountByTypeForCurrentMonth(TransactionType.INVESTMENT);
                break;
            default:
                return new BigDecimal(0);
        }
        if (result.isPresent()) {
            return result.get();
        } else {
            log.error("No record found for the type " + type);
            return new BigDecimal(0);
        }
    }

    public DashboardStats getCurrentMonthStats() {
        DashboardStats dashboardStats = new DashboardStats();
        dashboardStats.setRecentTransactions(getTransactions(1, 5).getData());
        BigDecimal totalExpense = getSumByTransactionType("EXPENSE");
        BigDecimal totalIncome = getSumByTransactionType("INCOME");
        BigDecimal totalInvestment = getSumByTransactionType("INVESTMENT");
        dashboardStats.setTotalExpense(totalExpense);
        dashboardStats.setTotalIncome(totalIncome);
        dashboardStats.setTotalInvestment(totalInvestment);
        dashboardStats.setTotalBalance(totalIncome.subtract(totalExpense.add(totalInvestment)));
        List<TransactionSummaryDto> transactionSummaryDtomin = repository.getMinTransactionsForCurrentMonth();
        List<TransactionSummaryDto> transactionSummaryDtomax = repository.getMaxTransactionsForCurrentMonth();
        dashboardStats.setMinIncome(transactionSummaryDtomin.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME))
                .findFirst().isPresent() ?
                transactionSummaryDtomin.stream().filter(t -> t.getType().equals(TransactionType.INCOME)).findFirst().get().getAmount() : new BigDecimal(0));

        dashboardStats.setMinExpense(transactionSummaryDtomin.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE)).findFirst().isPresent()
                ? transactionSummaryDtomin.stream()
                .filter(t -> t.getType().equals(TransactionType.EXPENSE)).findFirst().get().getAmount() : new BigDecimal(0));

        dashboardStats.setMinInvestment(transactionSummaryDtomin.stream().filter(t -> t.getType().equals(TransactionType.INVESTMENT)).findFirst().isPresent()?
                transactionSummaryDtomin.stream().filter(t -> t.getType().equals(TransactionType.INVESTMENT)).findFirst().get().getAmount(): new BigDecimal(0));

        dashboardStats.setMaxIncome(transactionSummaryDtomax.stream()
                .filter(t -> t.getType().equals(TransactionType.INCOME)).findFirst().isPresent() ?
                transactionSummaryDtomax.stream().filter(t -> t.getType().equals(TransactionType.INCOME)).findFirst().get().getAmount()
                : new BigDecimal(0));
        dashboardStats.setMaxExpense(transactionSummaryDtomax.stream().filter(t -> t.getType().equals(TransactionType.EXPENSE)).findFirst().isPresent() ?
                transactionSummaryDtomax.stream()
                        .filter(t -> t.getType().equals(TransactionType.EXPENSE)).findFirst().get().getAmount()
                : new BigDecimal(0));
        dashboardStats.setMaxInvestment(transactionSummaryDtomax.stream().filter(t -> t.getType().equals(TransactionType.INVESTMENT)).findFirst().isPresent()?
                transactionSummaryDtomax.stream()
                        .filter(t -> t.getType().equals(TransactionType.INVESTMENT))
                        .findFirst().get().getAmount(): new BigDecimal(0));

        List<ExpenseTransactionChartDto> chartDtos = repository.getExpenseTransactionsForChart();
        //dashboardStats.setChartData(chartDtos);
        List<?> chartSummaryDtos = repository.getTransactionChartSummary();
        for (Object o : chartSummaryDtos) {
            Object[] objectArr = (Object[]) o;
            if (objectArr[0] instanceof Date) {
                dashboardStats.getDates().add((Date) objectArr[0]);
            }
            dashboardStats.getIncomesSum().add((BigDecimal) objectArr[1]);
            dashboardStats.getExpensesSum().add((BigDecimal) objectArr[2]);
            dashboardStats.getInvestmentsSum().add((BigDecimal) objectArr[3]);
        }
        return dashboardStats;
    }

}
