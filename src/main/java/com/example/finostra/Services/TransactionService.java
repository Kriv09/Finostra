package com.example.finostra.Services;

import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionDouble;
import com.example.finostra.Entity.Transactions.TransactionSingle;
import com.example.finostra.Entity.UserCards.UserCard;
import com.example.finostra.Models.FinancialAnalyzer;
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

    /**
     * Validate Transaction, whether its values are null
     * @param transaction object transaction
     * @throws IllegalArgumentException if some values is null
     */
    private void validateTransaction(BaseTransaction transaction) {
        if (transaction.getTransactionDate() == null ||
                transaction.getAmount() == null ||
                transaction.getDescription() == null ||
                transaction.getTransactionType() == null) {
            throw new IllegalArgumentException("Required fields are missing for the transaction");
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

    /**
     * Map BaseTransaction object to TransactionDTO
     * @param transaction BaseTransaction object to map
     * @return TransactionDTO containing the mapped data
     */
    public TransactionDTO mapToDTO(BaseTransaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setUserCardId(transaction.getUserCard().getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionType(transaction.getTransactionType());

        if (transaction instanceof TransactionDouble transactionDouble) {
            dto.setReceiverUserCardNumber(transactionDouble.getReceiverUserCardNumber());
        } else if (transaction instanceof TransactionSingle single) {
            dto.setOperationPlace(single.getOperationPlace());
        }

        return dto;
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
            throw new EntityNotFoundException("Transaction not found");
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
            throw new EntityNotFoundException("User card not found");
        }

        return baseTransactionRepository.findBaseTransactionsByUserCard(isUserCard.get());
    }

    /**
     * Add a new transaction
     * @param transactionDTO Data Transfer Object containing transaction data
     * @return Saved BaseTransaction object
     * @throws EntityNotFoundException if the user card is not found
     * @throws IllegalArgumentException if the transaction type is unsupported
     */
    public BaseTransaction addTransaction(TransactionDTO transactionDTO) {
        Optional<UserCard> isUserCard = userCardRepository.findById(transactionDTO.getUserCardId());
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("User card not found");
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
                throw new IllegalArgumentException("Transaction type not supported");
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
            throw new EntityNotFoundException("Transaction not found");
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
            throw new EntityNotFoundException("Transaction not found");
        }
        BaseTransaction baseTransaction = isTransaction.get();

        if (transactionDTO.getTransactionType() != baseTransaction.getTransactionType()) {
            throw new IllegalArgumentException("Transaction type mismatch");
        }

        baseTransaction.setTransactionDate(transactionDTO.getTransactionDate());
        baseTransaction.setAmount(transactionDTO.getAmount());
        baseTransaction.setDescription(transactionDTO.getDescription());

        if (baseTransaction instanceof TransactionSingle transactionSingle) {
            transactionSingle.setOperationPlace(transactionDTO.getOperationPlace());
        }

        validateTransaction(baseTransaction);

        return baseTransactionRepository.save(baseTransaction);
    }

    /**
     * Analyze financial data for user card
     * @param userCardId ID of the user card
     * @return FinancialAnalyzer object containing analysis results
     */
    public FinancialAnalyzer fetchFinancialAnalysisForUserCard(Long userCardId) {
        List<BaseTransaction> transactions = fetchTransactionsByUserCardId(userCardId);
        if(transactions.isEmpty()) {
            return new FinancialAnalyzer();
        }

        double currentBalance = 0, totalExpenses = 0, totalIncome = 0, averageExpenses = 0, averageIncome = 0;
        int expenseCount = 0, incomeCount = 0;

        for(BaseTransaction transaction : transactions) {
            switch (transaction.getTransactionType()) {
                case DEPOSIT:
                    currentBalance += transaction.getAmount();
                    totalIncome += transaction.getAmount();
                    incomeCount++;
                    break;
                case WITHDRAW, PAYMENT:
                    currentBalance -= transaction.getAmount();
                    totalExpenses += transaction.getAmount();
                    expenseCount++;
                    break;
                case TRANSFER:
                    if (!(transaction instanceof TransactionDouble transactionDouble)) {
                        throw new IllegalArgumentException("Transaction must be of type TransactionDouble");
                    }

                    if(transactionDouble.getReceiverUserCardNumber().equals(transaction.getUserCard().getCardNumber()) ) {
                        currentBalance += transactionDouble.getAmount();
                        totalIncome += transactionDouble.getAmount();
                        incomeCount++;
                    } else {
                        currentBalance -= transactionDouble.getAmount();
                        totalExpenses += transactionDouble.getAmount();
                        expenseCount++;
                    }
                    break;
            }
        }

        averageExpenses = expenseCount == 0 ? 0 : totalExpenses / expenseCount;
        averageIncome = incomeCount == 0 ? 0 : totalIncome / incomeCount;

        FinancialAnalyzer financialAnalyzer = new FinancialAnalyzer();
        financialAnalyzer.setTotalTransactions(transactions.size());
        financialAnalyzer.setCurrentBalance(currentBalance);
        financialAnalyzer.setTotalExpenses(totalExpenses);
        financialAnalyzer.setTotalIncome(totalIncome);
        financialAnalyzer.setAverageExpenses(averageExpenses);
        financialAnalyzer.setAverageIncome(averageIncome);

        return financialAnalyzer;
    }

}
