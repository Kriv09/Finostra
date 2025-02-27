package com.example.finostra.Entity;

import com.example.finostra.Entity.Transactions.TransactionCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FinancialAnalyzer {

    private Integer totalTransactions;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    private Double balance;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double totalExpenses;
    private Double totalIncome;

    private Double averageExpenses;
    private Double averageIncome;

}
