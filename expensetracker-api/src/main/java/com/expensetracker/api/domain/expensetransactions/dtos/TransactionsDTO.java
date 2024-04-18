package com.expensetracker.api.domain.expensetransactions.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class TransactionsDTO {
    private List<ExpenseTransactionResponse> data;
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
        List<ExpenseTransactionResponse> content= transactionPage.getContent()
                .stream()
                .map(ExpenseTransactionResponse::from)
                .toList();
        this.setData(content);
        this.setTotalElements(transactionPage.getTotalElements());
        this.setTotalPages(transactionPage.getTotalPages());
        this.setCurrentPage(transactionPage.getNumber() +1);
        this.setFirst(transactionPage.isFirst());
        this.setLast(transactionPage.isLast());
        this.setHasNext(transactionPage.hasNext());
        this.setHasPrevious(transactionPage.hasPrevious());
    }
}
