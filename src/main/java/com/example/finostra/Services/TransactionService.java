package com.example.finostra.Services;

import com.example.finostra.Entity.*;
import com.example.finostra.Repositories.BaseTransactionRepository;
import com.example.finostra.Repositories.UserCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.finostra.Entity.DTO.TransactionDTO;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final UserCardRepository userCardRepository;
    private final BaseTransactionRepository baseTransactionRepository;

    public TransactionService(UserCardRepository userCardRepository, BaseTransactionRepository baseTransactionRepository) {
        this.userCardRepository = userCardRepository;
        this.baseTransactionRepository = baseTransactionRepository;
    }

    // Validate transaction
    private void validateTransaction(BaseTransaction transaction) {
        if (transaction.getTransactionDate() == null ||
                transaction.getAmount() == null ||
                transaction.getDescription() == null ||
                transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Required fields are missing for the transaction");
        }
    }

    // Create TransactionSingle
    private TransactionSingle createTransactionSingle(TransactionDTO transactionDTO, UserCard userCard) {
        if(transactionDTO.getOperationPlace() == null) {
            throw new IllegalArgumentException("Required fields are missing for the transaction");
        }

        TransactionSingle transactionSingle = new TransactionSingle();

        transactionSingle.setTransactionDate(transactionDTO.getTransactionDate());
        transactionSingle.setAmount(transactionDTO.getAmount());
        transactionSingle.setDescription(transactionDTO.getDescription());
        transactionSingle.setTransactionType(transactionDTO.getTransactionType());
        transactionSingle.setUserCard(userCard);

        transactionSingle.setOperationPlace(transactionDTO.getOperationPlace());

        return transactionSingle;

    }

    // Create TransactionDouble
    private TransactionDouble createTransactionDouble(TransactionDTO transactionDTO, UserCard userCard) {
        if(transactionDTO.getReceiverUserCardNumber() == null) {
            throw new IllegalArgumentException("Required fields are missing for the transaction");
        }

        UserCard receiver = userCardRepository.findByCardNumber(transactionDTO.getReceiverUserCardNumber());
        if(receiver == null) {
            throw new EntityNotFoundException("User card not found");
        }

        TransactionDouble transactionDouble = new TransactionDouble();

        transactionDouble.setTransactionDate(transactionDTO.getTransactionDate());
        transactionDouble.setAmount(transactionDTO.getAmount());
        transactionDouble.setDescription(transactionDTO.getDescription());
        transactionDouble.setTransactionType(transactionDTO.getTransactionType());
        transactionDouble.setUserCard(userCard);

        transactionDouble.setSenderUserCardNumber(userCard.getCardNumber());
        transactionDouble.setReceiverUserCardNumber(receiver.getCardNumber());

        return transactionDouble;
    }

    // get all transactions
    public List<BaseTransaction> fetchTransactions() {
        return baseTransactionRepository.findAll();
    }

    // get by id
    public BaseTransaction fetchTransactionById(Long id) {
        Optional<BaseTransaction> transaction = baseTransactionRepository.findById(id);
        if(transaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction not found");
        }
        return transaction.get();
    }

    // add transaction
    public BaseTransaction addTransaction(TransactionDTO transactionDTO) {
        Optional<UserCard> isUserCard = userCardRepository.findById(transactionDTO.getUserCardId());
        if(isUserCard.isEmpty()) {
            throw new EntityNotFoundException("User card not found");
        }
        UserCard userCard = isUserCard.get();

        BaseTransaction baseTransaction;

        switch (transactionDTO.getTransactionType()) {
            case DEPOSIT, WITHDRAW:
                baseTransaction = createTransactionSingle(transactionDTO, userCard);
                break;
            case TRANSFER, PAYMENT:
                baseTransaction = createTransactionDouble(transactionDTO, userCard);
                break;
            default:
                throw new IllegalArgumentException("Transaction type not supported");
        }

        validateTransaction(baseTransaction);

        return baseTransactionRepository.save(baseTransaction);
    }



}
