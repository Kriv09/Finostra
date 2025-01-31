package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        List<TransactionDTO> transactions = transactionService.fetchTransactions()
                .stream()
                .map(BaseTransaction::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        try {
            BaseTransaction transaction = transactionService.fetchTransactionById(id);
            return ResponseEntity.ok(BaseTransaction.mapToDTO(transaction));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/userCard/{id}")
    public ResponseEntity<?> getTransactionsByUserCardId(@PathVariable Long id) {
        try {
            List<TransactionDTO> transactions = transactionService.fetchTransactionsByUserCardId(id)
                    .stream()
                    .map(BaseTransaction::mapToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(transactions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDto) {
        try {
            transactionService.addTransaction(transactionDto);
            return ResponseEntity.ok("Transaction is added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDto) {
        try {
            transactionService.updateTransaction(id, transactionDto);
            return ResponseEntity.ok("Transaction is updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction is not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid transaction data: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransactionById(id);
            return ResponseEntity.ok("Transaction is deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction is not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid transaction ID: " + e.getMessage());
        }
    }
}