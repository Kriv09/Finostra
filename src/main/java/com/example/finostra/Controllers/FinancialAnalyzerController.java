package com.example.finostra.Controllers;

import com.example.finostra.Entity.FinancialAnalyzer;
import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Services.FinancialAnalyzerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/analyzer")
public class FinancialAnalyzerController {
    private final FinancialAnalyzerService financialAnalyzerService;

    public FinancialAnalyzerController(FinancialAnalyzerService financialAnalyzerService) {
        this.financialAnalyzerService = financialAnalyzerService;
    }

    @GetMapping("/userCard/{id}")
    public ResponseEntity<?> getFinancialAnalyzerForUser(@PathVariable Long id,
                                                                         @RequestParam(required = false) TransactionCategory category,
                                                                         @RequestParam(required = false) LocalDateTime startDate,
                                                                         @RequestParam(required = false) LocalDateTime endDate) {
        try {
            FinancialAnalyzer financialAnalyzer = financialAnalyzerService.getFinancialAnalyzerForUserCard(id, category, startDate, endDate);
            return ResponseEntity.ok(financialAnalyzer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
