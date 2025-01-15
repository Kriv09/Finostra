package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<BaseTransaction>> getTransactions() {
        List<BaseTransaction> transactions = transactionService.fetchTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseTransaction> getTransaction(@PathVariable Long id) {
        try {
            BaseTransaction transaction = transactionService.fetchTransactionById(id);
            return ResponseEntity.ok(transaction);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/userCard_{id}")
    public ResponseEntity<List<BaseTransaction>> getTransactionsByUserCardId(@PathVariable Long id) {
        try {
            List<BaseTransaction> transactions = transactionService.fetchTransactionsByUserCardId(id);
            return ResponseEntity.ok(transactions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transactionDto) {
        try {
            transactionService.addTransaction(transactionDto);
            return ResponseEntity.ok("Transaction added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDto) {
        try {
            transactionService.updateTransaction(id, transactionDto);
            return ResponseEntity.ok("Transaction updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransactionById(id);
            return ResponseEntity.ok("Transaction deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
