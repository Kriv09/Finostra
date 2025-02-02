package com.example.finostra.Entity;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Entity.Transactions.TransactionDouble;
import com.example.finostra.Entity.Transactions.TransactionSingle;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

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

    public FinancialAnalyzer() {
        this.totalTransactions = null;
        this.balance = null;
        this.startDateTime = null;
        this.endDateTime = null;
        this.totalExpenses = null;
        this.totalIncome = null;
        this.averageExpenses = null;
        this.averageIncome = null;
        this.category = null;
    }

    // Getters and Setters

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public Integer getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Integer totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getAverageExpenses() {
        return averageExpenses;
    }

    public void setAverageExpenses(Double averageExpenses) {
        this.averageExpenses = averageExpenses;
    }

    public Double getAverageIncome() {
        return averageIncome;
    }

    public void setAverageIncome(Double averageIncome) {
        this.averageIncome = averageIncome;
    }

}
