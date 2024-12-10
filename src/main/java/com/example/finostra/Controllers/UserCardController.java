package com.example.finostra.Controllers;

import com.example.finostra.Entity.UserCard;
import com.example.finostra.Services.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userCard")
public class UserCardController {
    @Autowired
    private final UserCardService userCardService;

    public UserCardController(UserCardService userCardService) {
        this.userCardService = userCardService;
    }

    @PostMapping
    public ResponseEntity<String> addUserCard(@RequestBody UserCard userCard) {
        userCardService.addCard(userCard);
        return ResponseEntity.ok("User Card Added");
    }

    @GetMapping
    public ResponseEntity<List<UserCard>> getUserCards() {
        return ResponseEntity.ok(userCardService.fetchCards());
    }

}
