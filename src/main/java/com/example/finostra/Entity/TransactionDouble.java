package com.example.finostra.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionDouble extends BaseTransaction{

    private String senderUserCardNumber;

    private String receiverUserCardNumber;


    // Getters and Setters
    public String getSenderUserCardNumber() {
        return senderUserCardNumber;
    }

    public void setSenderUserCardNumber(String senderUserCardNumber) {
        this.senderUserCardNumber = senderUserCardNumber;
    }

    public String getReceiverUserCardNumber() {
        return receiverUserCardNumber;
    }

    public void setReceiverUserCardNumber(String receiverUserCardNumber) {
        this.receiverUserCardNumber = receiverUserCardNumber;
    }
}
