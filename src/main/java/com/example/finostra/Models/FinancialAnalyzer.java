package com.example.finostra.Models;

import java.time.LocalDateTime;

public class FinancialAnalyzer {

    private Integer totalTransactions;

    private Double balance;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double totalExpenses;
    private Double totalIncome;

    private Double averageExpenses;
    private Double averageIncome;

    public FinancialAnalyzer() {
        this.totalTransactions = 0;
        this.balance = 0.0;
        this.startDateTime = null;
        this.endDateTime = null;
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
