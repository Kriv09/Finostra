package com.example.finostra.Services.Transaction;

import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionDouble;
import com.example.finostra.Entity.Transactions.TransactionSingle;
import com.example.finostra.Entity.User.UserCards.UserCard;
import com.example.finostra.Exceptions.TransactionBadRequestException;
import com.example.finostra.Exceptions.TransactionNotFoundException;
import com.example.finostra.Exceptions.UserCardBadRequestException;
import com.example.finostra.Exceptions.UserCardNotFoundException;
import com.example.finostra.Repositories.Transaction.BaseTransactionRepository;
import com.example.finostra.Repositories.User.Card.UserCardRepository;
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

    private void validateTransaction(BaseTransaction transaction) {
        if (transaction.getTransactionDate() == null) {
            throw new TransactionBadRequestException("Transaction date is required");
        }
        if (transaction.getAmount() == null) {
            throw new TransactionBadRequestException("Transaction amount is required");
        }
        if (transaction.getDescription() == null) {
            throw new TransactionBadRequestException("Transaction description is required");
        }
        if (transaction.getTransactionType() == null) {
            throw new TransactionBadRequestException("Transaction type is required");
        }
        if (transaction.getCategory() == null) {
            throw new TransactionBadRequestException("Transaction category is required");
        }

        if (transaction instanceof TransactionSingle transactionSingle) {

            if (transactionSingle.getOperationPlace() == null) {
                throw new TransactionBadRequestException("Transaction operation place is required");
            }
        } else if (transaction instanceof TransactionDouble transactionDouble) {

            if(transactionDouble.getSenderUserCardNumber() == null) {
                throw new TransactionBadRequestException("Transaction sender card number is required");
            } else if(transactionDouble.getReceiverUserCardNumber() == null) {
                throw new TransactionBadRequestException("Transaction receiver card number is required");
            }
        }

    }

    private TransactionSingle createTransactionSingle(TransactionDTO transactionDTO, UserCard userCard) {
        if(transactionDTO.getOperationPlace() == null) {
            throw new TransactionBadRequestException("Required field operation place are missing for the transaction");
        }

        TransactionSingle transactionSingle = new TransactionSingle();

        transactionSingle.setTransactionDate(transactionDTO.getTransactionDate());
        transactionSingle.setAmount(transactionDTO.getAmount());
        transactionSingle.setDescription(transactionDTO.getDescription());
        transactionSingle.setTransactionType(transactionDTO.getTransactionType());
        transactionSingle.setCategory(transactionDTO.getCategory());
        transactionSingle.setUserCard(userCard);

        transactionSingle.setOperationPlace(transactionDTO.getOperationPlace());

        return transactionSingle;

    }

    private TransactionDouble createTransactionDouble(TransactionDTO transactionDTO, UserCard userCard) {
        if(transactionDTO.getReceiverUserCardNumber() == null) {
            throw new TransactionBadRequestException("Required field card number are missing for the transaction");
        }

        UserCard receiver = userCardRepository.findByCardNumber(transactionDTO.getReceiverUserCardNumber());
        if(receiver == null) {
            throw new UserCardNotFoundException("User card not found");
        }

        TransactionDouble transactionDouble = new TransactionDouble();

        transactionDouble.setTransactionDate(transactionDTO.getTransactionDate());
        transactionDouble.setAmount(transactionDTO.getAmount());
        transactionDouble.setDescription(transactionDTO.getDescription());
        transactionDouble.setTransactionType(transactionDTO.getTransactionType());
        transactionDouble.setCategory(transactionDTO.getCategory());
        transactionDouble.setUserCard(userCard);

        transactionDouble.setSenderUserCardNumber(userCard.getCardNumber());
        transactionDouble.setReceiverUserCardNumber(receiver.getCardNumber());

        return transactionDouble;
    }

    public List<BaseTransaction> fetchTransactions() {
        return baseTransactionRepository.findAll();
    }

    public BaseTransaction fetchTransactionById(Long id) {
        Optional<BaseTransaction> transaction = baseTransactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        return transaction.get();
    }

    public List<BaseTransaction> fetchTransactionsByUserCardId(Long userCardId) {
        if (userCardId == null) {
            throw new UserCardBadRequestException("Required field userCardId is missing for the transaction");
        }

        Optional<UserCard> isUserCard = userCardRepository.findById(userCardId);
        if (isUserCard.isEmpty()) {
            throw new UserCardNotFoundException("User card not found");
        }

        return baseTransactionRepository.findBaseTransactionsByUserCard(isUserCard.get());
    }

    public BaseTransaction addTransaction(TransactionDTO transactionDTO) {
        Optional<UserCard> isUserCard = userCardRepository.findById(transactionDTO.getUserCardId());
        if (isUserCard.isEmpty()) {
            throw new UserCardNotFoundException("User card not found");
        }
        UserCard userCard = isUserCard.get();

        BaseTransaction baseTransaction;

        switch (transactionDTO.getTransactionType()) {
            case DEPOSIT, WITHDRAW, PAYMENT:
                baseTransaction = createTransactionSingle(transactionDTO, userCard);
                break;
            case TRANSFER:
                baseTransaction = createTransactionDouble(transactionDTO, userCard);
                break;
            default:
                throw new TransactionBadRequestException("Transaction type is not supported");
        }

        validateTransaction(baseTransaction);

        return baseTransactionRepository.save(baseTransaction);
    }

    public void deleteTransactionById(Long id) {
        if (id == null) {
            throw new TransactionBadRequestException("Required field ID is missing for the transaction");
        }
        Optional<BaseTransaction> transaction = baseTransactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        baseTransactionRepository.delete(transaction.get());
    }

    public BaseTransaction updateTransaction(Long id, TransactionDTO transactionDTO) {
        if (id == null) {
            throw new TransactionBadRequestException("Required field ID is missing for the transaction");
        } else if (transactionDTO == null) {
            throw new TransactionBadRequestException("Required field transaction data is missing for the transaction");
        }

        Optional<BaseTransaction> isTransaction = baseTransactionRepository.findById(id);
        if (isTransaction.isEmpty()) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        BaseTransaction baseTransaction = isTransaction.get();

        if (transactionDTO.getTransactionType() != baseTransaction.getTransactionType()) {
            throw new TransactionBadRequestException("Transaction type mismatch");
        }

        baseTransaction.setCategory(transactionDTO.getCategory());
        baseTransaction.setTransactionDate(transactionDTO.getTransactionDate());
        baseTransaction.setAmount(transactionDTO.getAmount());
        baseTransaction.setDescription(transactionDTO.getDescription());

        if (baseTransaction instanceof TransactionSingle transactionSingle) {
            transactionSingle.setOperationPlace(transactionDTO.getOperationPlace());
        } else if (baseTransaction instanceof TransactionDouble transactionDouble) {
            transactionDouble.setReceiverUserCardNumber(transactionDTO.getReceiverUserCardNumber());
            transactionDouble.setSenderUserCardNumber(transactionDTO.getSenderUserCardNumber());
        }

        validateTransaction(baseTransaction);

        return baseTransactionRepository.save(baseTransaction);
    }
}