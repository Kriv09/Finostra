package com.example.finostra.Controllers;

import com.example.finostra.Services.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {

    @Autowired
    private final CurrencyConverterService currencyConverterService;

    public CurrencyController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/convert")
    public ResponseEntity<Double> convertCurrency(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to
    ) {
        double convertedAmount = currencyConverterService.convert(amount, from, to);
        return ResponseEntity.ok(convertedAmount);
    }

    @PostMapping("/rate")
    public ResponseEntity<String> updateExchangeRate(
            @RequestParam String currency,
            @RequestParam double rate
    ) {
        currencyConverterService.updateExchangeRate(currency, rate);
        return ResponseEntity.ok("Exchange rate updated successfully");
    }

    @DeleteMapping("/rate")
    public ResponseEntity<String> removeCurrency(@RequestParam String currency) {
        currencyConverterService.removeCurrency(currency);
        return ResponseEntity.ok("Currency removed successfully");
    }
}
