package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Models.FinancialAnalyzer;
import com.example.finostra.Services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
                .map(transactionService::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        try {
            BaseTransaction transaction = transactionService.fetchTransactionById(id);
            return ResponseEntity.ok(transactionService.mapToDTO(transaction));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/userCard_{id}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserCardId(@PathVariable Long id) {
        try {
            List<TransactionDTO> transactions = transactionService.fetchTransactionsByUserCardId(id)
                    .stream()
                    .map(transactionService::mapToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(transactions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/analyse_userCard_{id}")
    public ResponseEntity<FinancialAnalyzer> getFinancialAnalyzerForUser(@PathVariable Long id,
                                                                         @RequestParam(required = false) LocalDateTime startDate,
                                                                         @RequestParam(required = false) LocalDateTime endDate) {
        try {
            FinancialAnalyzer financialAnalyzer = transactionService.fetchFinancialAnalysisForUserCard(id, startDate, endDate);
            return ResponseEntity.ok(financialAnalyzer);
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
