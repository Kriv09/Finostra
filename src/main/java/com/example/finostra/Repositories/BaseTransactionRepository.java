package com.example.finostra.Repositories;

import com.example.finostra.Entity.BaseTransaction;
import com.example.finostra.Entity.TransactionType;
import com.example.finostra.Entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseTransactionRepository extends JpaRepository<BaseTransaction, Long> {
    List<BaseTransaction> findBaseTransactionsByTransactionType(TransactionType transactionType);
    List<BaseTransaction> findBaseTransactionsByUserCard(UserCard userCard);
}
