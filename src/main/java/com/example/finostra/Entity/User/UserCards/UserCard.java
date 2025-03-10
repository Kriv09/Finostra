package com.example.finostra.Entity.User.UserCards;

import com.example.finostra.Entity.User.User;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
@Table(name = "user_card")
public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private LocalDate expirationDate;

    private String ownerName;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
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
