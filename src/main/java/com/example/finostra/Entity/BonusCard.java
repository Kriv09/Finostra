package com.example.finostra.Entity;
import java.util.ArrayList;
import java.util.List;

public class BonusCard {
    private String ownerName;
    private String cardNumber;
    private double bonusBalance;
    private List<Transaction> transactions;

    private static final double CASHBACK_PERCENTAGE = 0.05; // 5% cashback

    public BonusCard(String ownerName, String cardNumber) {
        this.ownerName = ownerName;
        this.cardNumber = cardNumber;
        this.bonusBalance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(double amount) {
        Transaction transaction = new Transaction(amount);
        transactions.add(transaction);
        updateBonusBalance(amount);
    }

    private void updateBonusBalance(double amount) {
        this.bonusBalance += amount * CASHBACK_PERCENTAGE;
    }

    public double getBonusBalance() {
        return bonusBalance;
    }

    public double redeemCashback() {
        double cashback = bonusBalance;
        bonusBalance = 0.0;
        return cashback;
    }

    public String getCardDetails() {
        return "Owner: " + ownerName + ", Card Number: " + cardNumber;
    }
}
