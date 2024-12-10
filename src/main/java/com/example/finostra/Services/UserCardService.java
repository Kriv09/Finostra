package com.example.finostra.Services;

import com.example.finostra.Entity.UserCard;
import com.example.finostra.Repositories.UserCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCardService {

    private final UserCardRepository userCardRepository;

    public UserCardService(UserCardRepository userCardRepository) {
        this.userCardRepository = userCardRepository;
    }

    // Checking all rows
    private void validateCard(UserCard userCard) {
        if (userCard.getCardNumber() == null || userCard.getCardType() == null ||
                userCard.getExpirationDate() == null || userCard.getOwnerName() == null) {
            throw new IllegalArgumentException("Required fields are missing for the card");
        }
    }

    // Get all cards
    public List<UserCard> fetchCards() {
        return userCardRepository.findAll();
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
            throw new IllegalArgumentException("Card not found");
        }

        UserCard userCard = isUserCard.get();
        userCard.setCardNumber(newUserCard.getCardNumber());
        userCard.setCardType(newUserCard.getCardType());
        userCard.setExpirationDate(newUserCard.getExpirationDate());
        userCard.setOwnerName(newUserCard.getOwnerName());
        userCard.setActive(newUserCard.isActive());

        return userCardRepository.save(userCard);
    }

    // delete card
    public void deleteCardById(Long id) {
        Optional<UserCard> isUserCard = userCardRepository.findById(id);
        if (isUserCard.isEmpty()) {
            throw new IllegalArgumentException("Card not found");
        }
        userCardRepository.delete(isUserCard.get());
    }
}
