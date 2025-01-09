package com.example.finostra.Controllers;

import com.example.finostra.Entity.DTO.CardToUserDto;
import com.example.finostra.Entity.UserCard;
import com.example.finostra.Repositories.UserCardRepository;
import com.example.finostra.Services.UserCardService;
import com.example.finostra.Services.UserService;
import com.example.finostra.Utils.UserCardUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userCard")
public class UserCardController {

    private final UserCardService userCardService;
    private final UserService userService;
    private final UserCardRepository userCardRepository;

    public UserCardController(UserCardService userCardService, UserService userService, UserCardRepository userCardRepository) {
        this.userCardService = userCardService;
        this.userService = userService;
        this.userCardRepository = userCardRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserCard>> getUserCards() {
        return ResponseEntity.ok(userCardService.fetchCards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCard> getUserCard(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userCardService.fetchCardById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/user_{id}")
    public ResponseEntity<List<UserCard>> getUserCardsByUserId(@PathVariable long id) {
        try{
            return ResponseEntity.ok(userCardService.fetchCardsByUserId(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/assign")
    public  ResponseEntity<String> assignCardToUser(@RequestBody CardToUserDto cardToUserDto) {
        try {
            String UniqueIBAN = UserCardUtils.generateIBAN(UserCard.mapDTO(cardToUserDto, userService.getById(cardToUserDto.getUserId())));
            userCardService.getByCardNumber(cardToUserDto.getCardNumber()).setIBAN(UniqueIBAN);
          userCardService.assignCardToUser(cardToUserDto);

          return ResponseEntity.ok("Assigned card to user");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> addUserCard(@RequestBody CardToUserDto userCard) {
        try {
            userCardService.assignCardToUser(userCard);
            return ResponseEntity.ok("User Card Added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserCard(@PathVariable Long id, @RequestBody UserCard newUserCard) {
        try {
            userCardService.updateCard(id, newUserCard);
            return ResponseEntity.ok("User Card Updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserCard(@PathVariable Long id) {
        try {
            userCardService.deleteCardById(id);
            return ResponseEntity.ok("User Card Deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
