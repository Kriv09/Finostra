package com.example.finostra.Entity;

import jakarta.persistence.Entity;

@Entity
public class TransactionSingle extends BaseTransaction{

    private String operationPlace;

    // Getters and Setters
    public String getOperationPlace() {
        return operationPlace;
    }
    public void setOperationPlace(String operationPlace) {
        this.operationPlace = operationPlace;
    }

}
