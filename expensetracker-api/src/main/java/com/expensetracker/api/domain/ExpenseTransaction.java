package com.expensetracker.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseTransaction {


    @Id
    @SequenceGenerator(name = "ex_trn_seq_gen",sequenceName = "ex_trn_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ex_trn_seq_gen")
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date",nullable = false)
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

}
