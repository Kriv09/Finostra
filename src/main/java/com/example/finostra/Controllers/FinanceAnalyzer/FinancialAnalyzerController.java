package com.example.finostra.Controllers.FinanceAnalyzer;

import com.example.finostra.Entity.Finances.FinancialAnalyzer;
import com.example.finostra.Entity.Transactions.TransactionCategory;
import com.example.finostra.Services.FinanceAnalyzer.FinancialAnalyzerService;
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
        FinancialAnalyzer financialAnalyzer = financialAnalyzerService.getFinancialAnalyzerForUserCard(id, category, startDate, endDate);
        return ResponseEntity.ok(financialAnalyzer);
    }
}
