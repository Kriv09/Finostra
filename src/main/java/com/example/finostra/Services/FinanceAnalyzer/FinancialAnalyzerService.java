package com.example.finostra.Services.FinanceAnalyzer;

import com.example.finostra.Entity.Finances.FinancialAnalyzer;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Entity.Transactions.TransactionDouble;
import com.example.finostra.Exceptions.TransactionBadRequestException;
import com.example.finostra.Services.Transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class FinancialAnalyzerService {

    private final TransactionService transactionService;

    public FinancialAnalyzerService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public FinancialAnalyzer getFinancialAnalyzerForUserCard(Long userCardId, TransactionCategory transactionCategory, LocalDateTime startDate, LocalDateTime endDate) {
        List<BaseTransaction> transactions = transactionService.fetchTransactionsByUserCardId(userCardId).stream()
                .filter(transaction -> {
                    LocalDateTime transactionDate = transaction.getTransactionDate();
                    TransactionCategory category = transaction.getCategory();
                    return (startDate == null || !transactionDate.isBefore(startDate)) &&
                            (endDate == null || !transactionDate.isAfter(endDate)) &&
                            (transactionCategory == null || transactionCategory.equals(category));
                })
                .sorted(Comparator.comparing(BaseTransaction :: getTransactionDate))
                .toList();

        if(transactions.isEmpty()) {
            return new FinancialAnalyzer();
        }

        double balance = 0, totalExpenses = 0, totalIncome = 0, averageExpenses = 0, averageIncome = 0;
        int expenseCount = 0, incomeCount = 0;

        for(BaseTransaction transaction : transactions) {
            switch (transaction.getTransactionType()) {
                case DEPOSIT:
                    balance += transaction.getAmount();
                    totalIncome += transaction.getAmount();
                    incomeCount++;
                    break;
                case WITHDRAW, PAYMENT:
                    balance -= transaction.getAmount();
                    totalExpenses += transaction.getAmount();
                    expenseCount++;
                    break;
                case TRANSFER:
                    if (!(transaction instanceof TransactionDouble transactionDouble)) {
                        throw new TransactionBadRequestException("Transaction must be of type TransactionDouble");
                    }

                    if(transactionDouble.getReceiverUserCardNumber().equals(transaction.getUserCard().getCardNumber()) ) {
                        balance += transactionDouble.getAmount();
                        totalIncome += transactionDouble.getAmount();
                        incomeCount++;
                    } else {
                        balance -= transactionDouble.getAmount();
                        totalExpenses += transactionDouble.getAmount();
                        expenseCount++;
                    }
                    break;
            }
        }


        if (expenseCount == 0) {
            averageExpenses = 0;
        } else {
            averageExpenses = totalExpenses / expenseCount;
        }
        if (incomeCount == 0) {
            averageIncome = 0;
        } else {
            averageIncome = totalIncome / incomeCount;
        }


        FinancialAnalyzer financialAnalyzer = new FinancialAnalyzer();
        financialAnalyzer.setStartDateTime(startDate);
        financialAnalyzer.setEndDateTime(endDate);
        financialAnalyzer.setCategory(transactionCategory);
        financialAnalyzer.setTotalTransactions(transactions.size());
        financialAnalyzer.setBalance(balance);
        financialAnalyzer.setTotalExpenses(totalExpenses);
        financialAnalyzer.setTotalIncome(totalIncome);
        financialAnalyzer.setAverageExpenses(averageExpenses);
        financialAnalyzer.setAverageIncome(averageIncome);

        return financialAnalyzer;
    }

}
