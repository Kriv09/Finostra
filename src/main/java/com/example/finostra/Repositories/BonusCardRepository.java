package com.example.finostra.Repositories;
import com.example.finostra.Entity.BonusCard;

public class BonusCardRepository {
    private BonusCard bonusCard;

    public BonusCardRepository(String ownerName, String cardNumber) {
        this.bonusCard = new BonusCard(ownerName, cardNumber);
    }

    public BonusCard getBonusCard() {
        return bonusCard;
    }
}