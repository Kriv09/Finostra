package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
<<<<<<< HEAD
    @Transactional
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transactionDto) {
=======
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDto) {
>>>>>>> main
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
<<<<<<< HEAD
    @Transactional
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDto) {
=======
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDto) {
>>>>>>> main
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
<<<<<<< HEAD
    @Transactional
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
=======
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
>>>>>>> main
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