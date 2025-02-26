package com.example.finostra.Exceptions;

import jakarta.persistence.EntityNotFoundException;

public class TransactionNotFoundException extends EntityNotFoundException {
    public TransactionNotFoundException(String message) {
      super(message);
    }
}
