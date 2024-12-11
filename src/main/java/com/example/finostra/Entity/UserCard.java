package com.example.finostra.Entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    @Enumerated(EnumType.STRING) // Persist the enum as a String in the database
    private CardType cardType; // Use CardType enum

    private LocalDate expirationDate;

    private String ownerName;

    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}



// SQL request, adding table user_card

//CREATE TABLE user_card (
//        id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Унікальний ідентифікатор карти
//        card_number VARCHAR(255),            -- Номер карти
//card_type VARCHAR(255),              -- Тип карти (наприклад, VISA, MasterCard)
//expiration_date DATE,                -- Дата закінчення терміну дії карти
//owner_name VARCHAR(255),             -- Ім'я власника карти
//is_active BOOLEAN                    -- Статус активності карти
//);
