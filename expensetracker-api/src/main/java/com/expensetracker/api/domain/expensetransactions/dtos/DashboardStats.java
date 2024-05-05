package com.expensetracker.api.domain.expensetransactions.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardStats {
    private List<ExpenseTransactionResponse> recentTransactions;
    private BigDecimal totalExpense;
    private BigDecimal totalIncome;
    private BigDecimal totalInvestment;
    private BigDecimal totalBalance;
    private BigDecimal minIncome;
    private BigDecimal maxIncome;
    private BigDecimal minExpense;
    private BigDecimal maxExpense;
    private BigDecimal minInvestment;
    private BigDecimal maxInvestment;
    private List<ExpenseTransactionChartDto> chartData;
}
