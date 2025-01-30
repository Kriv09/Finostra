package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.UserRegistrationDto;
import com.example.finostra.Entity.UserCard;
import com.example.finostra.Services.UserCardService;
import com.example.finostra.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserCardService userCardService;

    public UserController(UserService userService, UserCardService userCardService) {
        this.userService = userService;
        this.userCardService = userCardService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.registerNewUser(registrationDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/userCard/transaction")
    public ResponseEntity<String> addTransaction(@RequestParam String cardNumber, @RequestParam double transactionAmount) {
        try {
            userCardService.addTransaction(cardNumber, transactionAmount);
            return ResponseEntity.ok("Transaction processed and bonuses added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/userCard/redeem")
    public ResponseEntity<String> redeemBonuses(@RequestParam String username, @RequestParam double amount) {
        try {
            userCardService.redeemBonuses(username, amount);
            return ResponseEntity.ok("Bonuses redeemed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}