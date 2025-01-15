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

    // Create TransactionDouble
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

    // get all transactions by userCard id
    public List<BaseTransaction> fetchTransactionsByUserCardId(Long userCardId) {
        if(userCardId == null) {
            throw new IllegalArgumentException("Required field userCardId are missing for the transaction");
        }

        Optional<UserCard> isUserCard = userCardRepository.findById(userCardId);
        if(isUserCard.isEmpty()) {
            throw new EntityNotFoundException("User card not found");
        }

        return baseTransactionRepository.findBaseTransactionsByUserCard(isUserCard.get());
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

    // delete transaction
    public void deleteTransactionById(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Required field ID are missing for the transaction");
        }
        Optional<BaseTransaction> transaction = baseTransactionRepository.findById(id);
        if(transaction.isEmpty()) {
            throw new EntityNotFoundException("Transaction not found");
        }
        baseTransactionRepository.delete(transaction.get());
    }

    // update transaction
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
        } else if (baseTransaction instanceof TransactionDouble transactionDouble) {
            if (transactionDTO.getReceiverUserCardNumber() != null) {
                UserCard receiver = userCardRepository.findByCardNumber(transactionDTO.getReceiverUserCardNumber());
                if (receiver == null) {
                    throw new EntityNotFoundException("Receiver user card not found");
                }
                transactionDouble.setReceiverUserCardNumber(receiver.getCardNumber());
            }
        }

        validateTransaction(baseTransaction);

        return baseTransactionRepository.save(baseTransaction);
    }



    // Analyze Finances
    public FinancialAnalyzer fetchFinancialAnalysisForUserCard(Long userCardId) {
        List<BaseTransaction> transactions = fetchTransactionsByUserCardId(userCardId);

        if(transactions.isEmpty()) {
            return new FinancialAnalyzer();
        }

        FinancialAnalyzer financialAnalyzer = new FinancialAnalyzer();

        financialAnalyzer.setTrasactions(transactions);

        financialAnalyzer.setTotalTransactions(transactions.size());

        double currentBalance = 0, totalExpenses = 0, totalIncome = 0, averageExpenses = 0, averageIncome = 0;

        for(BaseTransaction transaction : transactions) {
            if(transaction instanceof TransactionSingle transactionSingle) {
                switch (transaction.getTransactionType()) {
                    case DEPOSIT:
                        currentBalance += transactionSingle.getAmount();
                        break;
                    case WITHDRAW:
                        currentBalance -= transactionSingle.getAmount();
                        break;
                }

            } else if(transaction instanceof TransactionDouble transactionDouble) {
                switch (transaction.getTransactionType()) {
                    case TRANSFER, PAYMENT:
                        if(( ((TransactionDouble) transaction).getReceiverUserCardNumber() )
                                .equals(transaction.getUserCard().getCardNumber()) ) {
                            currentBalance += transactionDouble.getAmount();
                        } else {
                            currentBalance -= transactionDouble.getAmount();
                        }
                        break;
                }
            }
        }

        financialAnalyzer.setCurrentBalance(currentBalance);

        return financialAnalyzer;
    }


}
