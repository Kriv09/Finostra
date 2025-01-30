package com.example.finostra.Services;
import com.example.finostra.Entity.BonusCard;
import com.example.finostra.Repositories.BonusCardRepository;

public class BonusCardService {
    private BonusCardRepository repository;

    public BonusCardService(String ownerName, String cardNumber) {
        this.repository = new BonusCardRepository(ownerName, cardNumber);
    }

    public void addTransaction(double amount) {
        repository.getBonusCard().addTransaction(amount);
    }

    public double getBonusBalance() {
        return repository.getBonusCard().getBonusBalance();
    }

    public double redeemCashback() {
        return repository.getBonusCard().redeemCashback();
    }

    public String getCardDetails() {
        return repository.getBonusCard().getCardDetails();
    }
}

