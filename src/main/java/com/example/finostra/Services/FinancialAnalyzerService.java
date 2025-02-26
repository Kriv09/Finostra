package com.example.finostra.Services;

import com.example.finostra.Configuration.SecurityConfig;
import com.example.finostra.Entity.FinancialAnalyzer;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Entity.Transactions.TransactionDouble;
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
    /**
     * Analyse finance for user card
     * @param userCardId ID of the user card
     * @param  transactionCategory category of transactions, can be null to include all category
     * @param startDate  the start of the date range for transactions (inclusive), can be null to include all earlier transactions
     * @param endDate    the end of the date range for transactions (inclusive), can be null to include all later transactions
     * @return FinancialAnalyzer object containing analysis results
     */
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
                        throw new IllegalArgumentException("Transaction must be of type TransactionDouble");
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

        averageExpenses = expenseCount == 0 ? 0 : totalExpenses / expenseCount;
        averageIncome = incomeCount == 0 ? 0 : totalIncome / incomeCount;

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
