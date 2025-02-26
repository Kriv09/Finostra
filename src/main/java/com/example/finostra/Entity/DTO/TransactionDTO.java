package com.example.finostra.Entity.DTO;

import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Entity.Transactions.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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

}
