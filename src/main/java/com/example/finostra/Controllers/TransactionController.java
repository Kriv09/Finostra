package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.TransactionDTO;
import com.example.finostra.Entity.Transactions.BaseTransaction;
import com.example.finostra.Services.Transaction.TransactionService;
import jakarta.transaction.Transactional;
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
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        BaseTransaction transaction = transactionService.fetchTransactionById(id);
        return ResponseEntity.ok(BaseTransaction.mapToDTO(transaction));
    }

    @GetMapping("/userCard/{id}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserCardId(@PathVariable Long id) {
        List<TransactionDTO> transactions = transactionService.fetchTransactionsByUserCardId(id)
                .stream()
                .map(BaseTransaction::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transactionDto) {
        transactionService.addTransaction(transactionDto);
        return ResponseEntity.ok("Transaction is added successfully");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDto) {
        transactionService.updateTransaction(id, transactionDto);
        return ResponseEntity.ok("Transaction is updated successfully");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok("Transaction is deleted successfully");
    }
}
