package com.example.finostra.Controllers.UserCard;

import com.example.finostra.Entity.DTO.CardToUserDto;
import com.example.finostra.Entity.User.UserCards.UserCard;
import com.example.finostra.Services.User.Card.UserCardService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userCard")
public class UserCardController {

    private final UserCardService userCardService;

    public UserCardController(UserCardService userCardService) {
        this.userCardService = userCardService;
    }

    @GetMapping
    public ResponseEntity<List<UserCard>> getUserCards() {
        return ResponseEntity.ok(userCardService.fetchCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCard> getUserCard(@PathVariable Long id) {
        return ResponseEntity.ok(userCardService.fetchCardById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UserCard>> getUserCardsByUserId(@PathVariable long id) {
        return ResponseEntity.ok(userCardService.fetchCardsByUserId(id));
    }

    @PostMapping("/assign")
    @Transactional
    public ResponseEntity<String> assignCardToUser(@RequestBody CardToUserDto cardToUserDto) {
        userCardService.assignCardToUser(cardToUserDto);
        return ResponseEntity.ok("Card is assigned to user");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> addUserCard(@RequestBody CardToUserDto userCard) {
        userCardService.assignCardToUser(userCard);
        return ResponseEntity.ok("User Card is added successfully");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateUserCard(@PathVariable Long id, @RequestBody UserCard newUserCard) {
        userCardService.updateCard(id, newUserCard);
        return ResponseEntity.ok("User Card is updated successfully");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteUserCard(@PathVariable Long id) {
        userCardService.deleteCardById(id);
        return ResponseEntity.ok("User Card is deleted successfully");
    }
}
