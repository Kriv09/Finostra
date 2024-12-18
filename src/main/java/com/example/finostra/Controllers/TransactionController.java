package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.BaseTransaction;
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

    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transactionDto) {
        try {
            BaseTransaction savedTransaction = transactionService.addTransaction(transactionDto);
            return ResponseEntity.ok("Transaction added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
