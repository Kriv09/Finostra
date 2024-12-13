package com.example.finostra.Services;

import com.example.finostra.Entity.DTO.CardToUserDto;
import com.example.finostra.Entity.UserCard;
import com.example.finostra.Entity.User;
import com.example.finostra.Repositories.UserCardRepository;
import com.example.finostra.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCardService {

    private final UserRepository userRepository;

    private final UserCardRepository userCardRepository;

    public UserCardService(UserCardRepository userCardRepository, UserRepository userRepository) {
        this.userCardRepository = userCardRepository;
        this.userRepository = userRepository;
    }

    // Checking all card's rows
    private void validateCard(UserCard userCard) {
        if (userCard.getCardNumber() == null || userCard.getCardType() == null ||
                userCard.getExpirationDate() == null || userCard.getOwnerName() == null) {
            throw new IllegalArgumentException("Required fields are missing for the card");
        }
    }

    // Assign userCard to user
    public void assignCardToUser(CardToUserDto cardToUserDto) {

        Optional<User> user = userRepository.findById(cardToUserDto.getUserId());
        if(user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id " + cardToUserDto.getUserId());
        }
        if (userCardRepository.findByCardNumber(cardToUserDto.getCardNumber()) != null) {
            throw new IllegalArgumentException("Card number already exists");
        }

        UserCard userCard = new UserCard();
        userCard.setCardNumber(cardToUserDto.getCardNumber());
        userCard.setCardType(cardToUserDto.getCardType());
        userCard.setExpirationDate(cardToUserDto.getExpirationDate());
        userCard.setOwnerName(cardToUserDto.getOwnerName());
        userCard.setActive(true);
        userCard.setUser(user.get());

        validateCard(userCard);

        userCardRepository.save(userCard);

    }

    // Fetch cards by user id
    public List<UserCard> fetchCardsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id " + userId);
        }
        return userCardRepository.findByUser(user.get());
    }

    // Get all cards
    public List<UserCard> fetchCards() {
        return userCardRepository.findAll();
    }

    // Get card
    public UserCard fetchCardById(Long id) {
        Optional<UserCard> userCard = userCardRepository.findById(id);
        if (userCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }
        return userCard.get();
    }

    // Add card
    public UserCard addCard(UserCard userCard) {
        if (userCardRepository.findByCardNumber(userCard.getCardNumber()) != null) {
            throw new IllegalArgumentException("Card number already exists");
        }
        validateCard(userCard);
        return userCardRepository.save(userCard);
    }

    // Update card
    public UserCard updateCard(Long id, UserCard newUserCard) {
        if (id == null) {
            throw new IllegalArgumentException("Card id is null");
        }
        validateCard(newUserCard);

        Optional<UserCard> isUserCard = userCardRepository.findById(id);
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }

        UserCard userCard = isUserCard.get();
        userCard.setCardNumber(newUserCard.getCardNumber());
        userCard.setCardType(newUserCard.getCardType());
        userCard.setExpirationDate(newUserCard.getExpirationDate());
        userCard.setOwnerName(newUserCard.getOwnerName());
        userCard.setActive(newUserCard.getActive());

        return userCardRepository.save(userCard);
    }

    // delete card
    public void deleteCardById(Long id) {
        Optional<UserCard> isUserCard = userCardRepository.findById(id);
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }
        userCardRepository.delete(isUserCard.get());
    }
}
