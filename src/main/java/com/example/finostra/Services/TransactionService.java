package com.example.finostra.Services;

import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionDouble;
import com.example.finostra.Entity.Transactions.TransactionSingle;
import com.example.finostra.Entity.UserCards.UserCard;
import com.example.finostra.Entity.FinancialAnalyzer;
import com.example.finostra.Repositories.BaseTransactionRepository;
import com.example.finostra.Repositories.UserCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.finostra.Entity.DTO.TransactionDTO;

import java.time.LocalDateTime;
import java.util.Comparator;
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

    /**
     * Validate Transaction, whether its values are null
     * @param transaction object transaction
     * @throws IllegalArgumentException if some values is null
     */
    private void validateTransaction(BaseTransaction transaction) {
        if (transaction.getTransactionDate() == null) {
            throw new IllegalArgumentException("Transaction date is required");
        }
        if (transaction.getAmount() == null) {
            throw new IllegalArgumentException("Transaction amount is required");
        }
        if (transaction.getDescription() == null) {
            throw new IllegalArgumentException("Transaction description is required");
        }
        if (transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Transaction type is required");
        }
        if (transaction.getCategory() == null) {
            throw new IllegalArgumentException("Transaction category is required");
        }

    }

    /**
     * Create object TransactionSingle
     * @param transactionDTO Data Transfer Object containing transaction data
     * @param userCard User card associated with the transaction
     * @return TransactionSingle object with the initialized values
     */
    private TransactionSingle createTransactionSingle(TransactionDTO transactionDTO, UserCard userCard) {
        if(transactionDTO.getOperationPlace() == null) {
            throw new IllegalArgumentException("Required field operation place are missing for the transaction");
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

    /**
     * Create object TransactionDouble
     * @param transactionDTO Data Transfer Object containing transaction data
     * @param userCard User card initiating the transaction
     * @return TransactionDouble object with the initialized values
     */
    private TransactionDouble createTransactionDouble(TransactionDTO transactionDTO, UserCard userCard) {
        if(transactionDTO.getReceiverUserCardNumber() == null) {
            throw new IllegalArgumentException("Required field card number are missing for the transaction");
        }

        UserCard receiver = userCardRepository.findByCardNumber(transactionDTO.getReceiverUserCardNumber());
        if(receiver == null) {
            throw new EntityNotFoundException("User card is not found");
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

    /**
     * Fetch all transactions from the repository
     * @return List of BaseTransaction objects
     */
    public List<BaseTransaction> fetchTransactions() {
        return baseTransactionRepository.findAll();
    }

    /**
     * Fetch a transaction by its ID
     * @param id Transaction ID
     * @return BaseTransaction object
     * @throws EntityNotFoundException if the transaction is not found
     */
    public BaseTransaction fetchTransactionById(Long id) {
        Optional<BaseTransaction> transaction = baseTransactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction is not found");
        }
        return transaction.get();
    }

    /**
     * Fetch all transactions for a specific user card
     * @param userCardId ID of the user card
     * @return List of BaseTransaction objects
     * @throws IllegalArgumentException if userCardId is null
     * @throws EntityNotFoundException if the user card is not found
     */
    public List<BaseTransaction> fetchTransactionsByUserCardId(Long userCardId) {
        if (userCardId == null) {
            throw new IllegalArgumentException("Required field userCardId is missing for the transaction");
        }

        Optional<UserCard> isUserCard = userCardRepository.findById(userCardId);
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("User card is not found");
        }

        return baseTransactionRepository.findBaseTransactionsByUserCard(isUserCard.get());
    }

    /**
     * Add a new transaction
     * @param transactionDTO Data Transfer Object containing transaction data
     * @return Saved BaseTransaction object
     * @throws EntityNotFoundException if the user card is not found
     * @throws IllegalArgumentException if the transaction type is unsupported or some required fields are missing
     */
    public BaseTransaction addTransaction(TransactionDTO transactionDTO) {
        Optional<UserCard> isUserCard = userCardRepository.findById(transactionDTO.getUserCardId());
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("User card is not found");
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
                throw new IllegalArgumentException("Transaction type is not supported");
        }

        validateTransaction(baseTransaction);

        return baseTransactionRepository.save(baseTransaction);
    }

    /**
     * Delete a transaction by its ID
     * @param id Transaction ID
     * @throws IllegalArgumentException if ID is null
     * @throws EntityNotFoundException if the transaction is not found
     */
    public void deleteTransactionById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Required field ID is missing for the transaction");
        }
        Optional<BaseTransaction> transaction = baseTransactionRepository.findById(id);
        if (transaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction is not found");
        }
        baseTransactionRepository.delete(transaction.get());
    }

    /**
     * Update a transaction
     * @param id Transaction ID
     * @param transactionDTO Data Transfer Object containing updated transaction data
     * @return Updated BaseTransaction object
     * @throws IllegalArgumentException if ID or transaction data is null, or if types mismatch
     * @throws EntityNotFoundException if the transaction is not found
     */
    public BaseTransaction updateTransaction(Long id, TransactionDTO transactionDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Required field ID is missing for the transaction");
        } else if (transactionDTO == null) {
            throw new IllegalArgumentException("Required field transaction data is missing for the transaction");
        }

        Optional<BaseTransaction> isTransaction = baseTransactionRepository.findById(id);
        if (isTransaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction is not found");
        }
        BaseTransaction baseTransaction = isTransaction.get();

        if (transactionDTO.getTransactionType() != baseTransaction.getTransactionType()) {
            throw new IllegalArgumentException("Transaction type mismatch");
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