package com.example.finostra.Services;

import com.example.finostra.Entity.Transaction;
import com.example.finostra.Entity.UserCard;
import com.example.finostra.Repositories.TransactionRepository;
import com.example.finostra.Repositories.UserCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.finostra.Entity.DTO.TransactionDTO;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserCardRepository userCardRepository;

    public TransactionService(TransactionRepository transactionRepository, UserCardRepository userCardRepository) {
        this.transactionRepository = transactionRepository;
        this.userCardRepository = userCardRepository;
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

        Optional<UserCard> userCard = userCardRepository.findById(transactionDto.getUserCardId());
        if(userCard.isEmpty()) {
            throw new EntityNotFoundException("User card not found");
        }

        Transaction transaction = new Transaction();

        transaction.setUserCard(userCard.get());
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setTransactionType(transactionDto.getTransactionType());

        validateTransaction(transaction);

        return transactionRepository.save(transaction);
    }



}
