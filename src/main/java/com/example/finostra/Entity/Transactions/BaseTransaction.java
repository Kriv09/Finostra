package com.example.finostra.Entity.Transactions;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.UserCards.UserCard;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transaction")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDateTime transactionDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_card_id", nullable = false)
    private UserCard userCard;

    // Static methods

    /**
     * Map BaseTransaction object to TransactionDTO
     * @param transaction BaseTransaction object to map
     * @return TransactionDTO containing the mapped data
     */
    public static TransactionDTO mapToDTO(BaseTransaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setUserCardId(transaction.getUserCard().getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setCategory(transaction.getCategory());

        if (transaction instanceof TransactionDouble transactionDouble) {
            dto.setSenderUserCardNumber(transactionDouble.getSenderUserCardNumber());
            dto.setReceiverUserCardNumber(transactionDouble.getReceiverUserCardNumber());
        } else if (transaction instanceof TransactionSingle single) {
            dto.setOperationPlace(single.getOperationPlace());
        }

        return dto;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
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

    public UserCard getUserCard() {
        return userCard;
    }

    public void setUserCard(UserCard userCard) {
        this.userCard = userCard;
    }
}
