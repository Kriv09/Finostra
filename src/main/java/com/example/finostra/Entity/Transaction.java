package com.example.finostra.Entity;

public class Transaction {
    private double amount;
    private long timestamp;

    public Transaction(double amount) {
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}