package com.example.finostra.Entity.DTO;

import com.example.finostra.Entity.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long userCardId;
    private Double amount;
    private LocalDateTime transactionDate;
    private String description;
    private TransactionType transactionType;

    public TransactionDTO(Long userCardId, Double amount,
                          LocalDateTime transactionDate, String description,
                          TransactionType transactionType) {
        this.userCardId = userCardId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionType = transactionType;
    }

    // Getters and Setters
    public Long getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(Long userCardId) {
        this.userCardId = userCardId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
