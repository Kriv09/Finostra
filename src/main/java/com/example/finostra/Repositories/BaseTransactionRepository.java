package com.example.finostra.Repositories;

import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Entity.Transactions.TransactionType;
import com.example.finostra.Entity.UserCards.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseTransactionRepository extends JpaRepository<BaseTransaction, Long> {
    List<BaseTransaction> findBaseTransactionsByTransactionType(TransactionType transactionType);
    List<BaseTransaction> findBaseTransactionsByUserCard(UserCard userCard);
}
