package com.example.finostra.Entity.Transactions;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransactionDouble extends BaseTransaction{

    private String senderUserCardNumber;

    private String receiverUserCardNumber;

}
