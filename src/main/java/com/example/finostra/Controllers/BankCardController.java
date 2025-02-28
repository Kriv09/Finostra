package com.example.finostra.Controllers;

import com.example.finostra.Entity.BankCards.BankCard;
import com.example.finostra.Entity.BankCards.CVVCode;
import com.example.finostra.Entity.DTO.BankCardDTO;
import com.example.finostra.Entity.Requests.BankCards.BankCardRequest;
import com.example.finostra.Services.BankCards.BankCardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/bankCard")
public class BankCardController {

    private final BankCardService bankCardService;

    @Autowired
    public BankCardController(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @GetMapping
    public ResponseEntity<List<BankCard>> getAllBankCards() {
        return ResponseEntity.ok(bankCardService.fetchAllBankCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankCard> getBankCardById(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.fetchBankCardById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BankCard>> getBankCardByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.fetchBankCardsByUserId(id));
    }

    @GetMapping("/{id}/cvv")
    public ResponseEntity<CVVCode> getCvv(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.fetchOrGenerateCVV(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> addBankCard(@RequestBody BankCardRequest bankCardRequest) {
        bankCardService.createBankCard(bankCardRequest);
        return ResponseEntity.ok("Bank card added successfully");
    }

}
