package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.CardToUserDto;
import com.example.finostra.Entity.UserCards.UserCard;
import com.example.finostra.Services.UserCardService;
import jakarta.persistence.EntityNotFoundException;
<<<<<<< HEAD
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> main
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getUserCard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userCardService.fetchCardById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserCardsByUserId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userCardService.fetchCardsByUserId(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/assign")
<<<<<<< HEAD
    @Transactional
    public  ResponseEntity<String> assignCardToUser(@RequestBody CardToUserDto cardToUserDto) {
=======
    public ResponseEntity<?> assignCardToUser(@RequestBody CardToUserDto cardToUserDto) {
>>>>>>> main
        try {
            userCardService.assignCardToUser(cardToUserDto);
            return ResponseEntity.ok("Card is assigned to user");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
<<<<<<< HEAD
    @Transactional
    public ResponseEntity<String> addUserCard(@RequestBody CardToUserDto userCard) {
=======
    public ResponseEntity<?> addUserCard(@RequestBody CardToUserDto userCard) {
>>>>>>> main
        try {
            userCardService.assignCardToUser(userCard);
            return ResponseEntity.ok("User Card is added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    @Transactional
    public ResponseEntity<String> updateUserCard(@PathVariable Long id, @RequestBody UserCard newUserCard) {
=======
    public ResponseEntity<?> updateUserCard(@PathVariable Long id, @RequestBody UserCard newUserCard) {
>>>>>>> main
        try {
            userCardService.updateCard(id, newUserCard);
            return ResponseEntity.ok("User Card is updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
<<<<<<< HEAD
    @Transactional
    public ResponseEntity<String> deleteUserCard(@PathVariable Long id) {
=======
    public ResponseEntity<?> deleteUserCard(@PathVariable Long id) {
>>>>>>> main
        try {
            userCardService.deleteCardById(id);
            return ResponseEntity.ok("User Card Deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
