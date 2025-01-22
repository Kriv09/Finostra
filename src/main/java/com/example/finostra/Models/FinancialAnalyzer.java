package com.example.finostra.Models;

import com.example.finostra.Entity.Transactions.BaseTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FinancialAnalyzer {

    private Integer totalTransactions;

    private Double currentBalance;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double totalExpenses;
    private Double totalIncome;

    private Double averageExpenses;
    private Double averageIncome;

    public FinancialAnalyzer() {
        this.totalTransactions = 0;
        this.currentBalance = 0.0;
        this.startDateTime = LocalDateTime.now();
        this.endDateTime = LocalDateTime.now();
        this.totalExpenses = 0.0;
        this.totalIncome = 0.0;
        this.averageExpenses = 0.0;
        this.averageIncome = 0.0;
    }


    // Getters and Setters

    public Integer getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Integer totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
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
