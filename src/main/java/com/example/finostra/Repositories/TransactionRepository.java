package com.example.finostra.Repositories;

import com.example.finostra.Entity.Transaction;
import com.example.finostra.Entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionType(TransactionType transactionType);
}
