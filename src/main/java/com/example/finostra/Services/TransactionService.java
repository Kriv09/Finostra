package com.example.finostra.Services;

import com.example.finostra.Entity.Transaction;
import com.example.finostra.Repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.finostra.Entity.DTO.TransactionDTO;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private void validateTransaction(Transaction transaction) {
        if(transaction.getTransactionDate() == null ||
        transaction.getAmount() == null ||
        transaction.getDescription() == null ||
        transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Required fields are missing for the transaction");
        }
    }

    // get all transactions
    public List<Transaction> fetchTransactions() {
        return transactionRepository.findAll();
    }

    // get by id
    public Transaction fetchTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(transaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction not found");
        }
        return transaction.get();
    }

    // add transaction
    public Transaction addTransaction(TransactionDTO transactionDto) {
        Transaction transaction = new Transaction();

        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setTransactionType(transactionDto.getTransactionType());

        validateTransaction(transaction);

        return transactionRepository.save(transaction);
    }



}
