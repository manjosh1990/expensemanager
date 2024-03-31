package com.expensetracker.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class TransactionsDTO {
    private List<ExpenseTransactionDTO> data;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    @JsonProperty("isFirst")
    private boolean isFirst;
    @JsonProperty("isLast")
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public TransactionsDTO(Page<ExpenseTransactionDTO> transactionPage){
        this.setData(transactionPage.getContent());
        this.setTotalElements(transactionPage.getTotalElements());
        this.setTotalPages(transactionPage.getTotalPages());
        this.setCurrentPage(transactionPage.getNumber() +1);
        this.setFirst(transactionPage.isFirst());
        this.setLast(transactionPage.isLast());
        this.setHasNext(transactionPage.hasNext());
        this.setHasPrevious(transactionPage.hasPrevious());
    }
}
