package com.example.finostra.Entity.DTO;

import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Entity.Transactions.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id = null;

    private Long userCardId;
    private Double amount;
    private LocalDateTime transactionDate;
    private String description;
    private TransactionType transactionType;
    private TransactionCategory category;

    // Transaction double row
    private String senderUserCardNumber;

    private String receiverUserCardNumber;

    // Transaction single row
    private String operationPlace;

    // Getters and Setters

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public TransactionCategory getCategory() { return category; }

    public void setCategory(TransactionCategory category) { this.category = category; }

    public Long getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(Long userCardId) {
        this.userCardId = userCardId;
    }

    public Double getAmount() { return amount; }

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

    public String getOperationPlace() {
        return operationPlace;
    }

    public void setOperationPlace(String operationPlace) {
        this.operationPlace = operationPlace;
    }

    public String getReceiverUserCardNumber() {
        return receiverUserCardNumber;
    }

    public void setReceiverUserCardNumber(String receiverUserCardNumber) {
        this.receiverUserCardNumber = receiverUserCardNumber;
    }

    public String getSenderUserCardNumber() {
        return senderUserCardNumber;
    }

    public void setSenderUserCardNumber(String senderUserCardNumber) {
        this.senderUserCardNumber = senderUserCardNumber;
    }



}
