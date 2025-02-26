package com.example.finostra.Entity.Transactions;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.UserCards.UserCard;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transaction")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
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


}
