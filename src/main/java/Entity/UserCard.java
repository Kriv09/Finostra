package Entity;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class UserCard {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Card Number

    private String cardNumber;

    // Card Type
    private String cardType;

    // Expiration Date
    private LocalDate expirationDate;

    // Owner name
    private String ownerName;

    // Is active
    private boolean isActive;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
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
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    // Future update, connect card with user table
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

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
