package com.example.finostra.Services;

import com.example.finostra.Entity.DTO.CardToUserDto;
import com.example.finostra.Entity.UserCards.UserCard;
import com.example.finostra.Entity.User;
import com.example.finostra.Repositories.UserCardRepository;
import com.example.finostra.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserCardService {

    private final UserRepository userRepository;

    private final UserCardRepository userCardRepository;

    public UserCardService(UserCardRepository userCardRepository, UserRepository userRepository) {
        this.userCardRepository = userCardRepository;
        this.userRepository = userRepository;
    }

    /**
     * Validate the card's fields and ensure their correctness.
     * @param userCard the card to validate
     * @throws IllegalArgumentException if any field is missing or invalid
     */
    private void validateCard(UserCard userCard) {
        if (userCard.getCardNumber() == null || userCard.getCardType() == null ||
                userCard.getExpirationDate() == null || userCard.getOwnerName() == null ||
                userCard.getActive() == null) {
            throw new IllegalArgumentException("Required fields are missing for the card");
        }
        if (!Pattern.matches("^\\d{16}$", userCard.getCardNumber())) {
            throw new IllegalArgumentException("Card number is incorrect, must contain only numbers and have a length of 16");
        }
    }

    /**
     * Assign a user card to a user.
     * @param cardToUserDto contains the user ID and card details
     * @throws EntityNotFoundException if the user is not found
     * @throws IllegalArgumentException if the card number already exists
     */
    public void assignCardToUser(CardToUserDto cardToUserDto) {

        Optional<User> user = userRepository.findById(cardToUserDto.getUserId());
        if (user.isEmpty()) {
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

    /**
     * Fetch all cards associated with a specific user.
     * @param userId the ID of the user
     * @return a list of user cards
     * @throws EntityNotFoundException if the user is not found
     */
    public List<UserCard> fetchCardsByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id " + userId);
        }
        return userCardRepository.findByUser(user.get());
    }

    /**
     * Fetch all user cards.
     * @return a list of all user cards
     */
    public List<UserCard> fetchCards() {
        return userCardRepository.findAll();
    }

    /**
     * Fetch a specific card by its ID.
     * @param id the ID of the card
     * @return the user card
     * @throws EntityNotFoundException if the card is not found
     */
    public UserCard fetchCardById(Long id) {
        Optional<UserCard> userCard = userCardRepository.findById(id);
        if (userCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }
        return userCard.get();
    }

    /**
     * Add a new card to the database.
     * @param userCard the card to add
     * @return the saved user card
     * @throws IllegalArgumentException if the card number already exists
     */
    public UserCard addCard(UserCard userCard) {
        if (userCardRepository.findByCardNumber(userCard.getCardNumber()) != null) {
            throw new IllegalArgumentException("Card number already exists");
        }
        validateCard(userCard);
        return userCardRepository.save(userCard);
    }

    /**
     * Update an existing card.
     * @param id the ID of the card to update
     * @param newUserCard the new card details
     * @return the updated user card
     * @throws IllegalArgumentException if the card ID is null
     * @throws EntityNotFoundException if the card is not found
     */
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

    /**
     * Delete a card by its ID.
     * @param id the ID of the card to delete
     * @throws EntityNotFoundException if the card is not found
     */
    public void deleteCardById(Long id) {
        Optional<UserCard> isUserCard = userCardRepository.findById(id);
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }
        userCardRepository.delete(isUserCard.get());
    }
}
