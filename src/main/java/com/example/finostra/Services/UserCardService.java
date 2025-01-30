package com.example.finostra.Services;

import com.example.finostra.Entity.User;
import com.example.finostra.Entity.UserCard;
import com.example.finostra.Repositories.UserCardRepository;
import com.example.finostra.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCardService {

    private final UserCardRepository userCardRepository;
    private final UserRepository userRepository;

    public UserCardService(UserCardRepository userCardRepository, UserRepository userRepository) {
        this.userCardRepository = userCardRepository;
        this.userRepository = userRepository;
    }

    public List<UserCard> fetchCards() {
        return userCardRepository.findAll();
    }

    public UserCard fetchCardById(Long id) {
        Optional<UserCard> userCard = userCardRepository.findById(id);
        if (userCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }
        return userCard.get();
    }

    public UserCard addCard(UserCard userCard) {
        if (userCardRepository.findByCardNumber(userCard.getCardNumber()) != null) {
            throw new IllegalArgumentException("Card number already exists");
        }
        validateCard(userCard);
        return userCardRepository.save(userCard);
    }

    public void addTransaction(String cardNumber, double transactionAmount) {
        UserCard userCard = userCardRepository.findByCardNumber(cardNumber);
        if (userCard == null) {
            throw new IllegalArgumentException("Card not found");
        }

        double bonusPercentage = getBonusPercentage(userCard);
        double earnedBonuses = transactionAmount * bonusPercentage / 100;

        User owner = userRepository.findByUsername(userCard.getOwnerName());
        if (owner == null) {
            throw new IllegalArgumentException("Card owner not found");
        }

        owner.setBonusBalance(owner.getBonusBalance() + earnedBonuses);
        userRepository.save(owner);
    }

    private double getBonusPercentage(UserCard userCard) {
        switch (userCard.getCardType()) {
            case VISA:
                return 1.5;
            case MASTERCARD:
                return 2.0;
            default:
                return 1.0;
        }
    }
    //bonus costs
    public void redeemBonuses(String username, double amount) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (user.getBonusBalance() < amount) {
            throw new IllegalArgumentException("Insufficient bonus balance");
        }

        user.setBonusBalance(user.getBonusBalance() - amount);
        userRepository.save(user);
    }

    public void validateCard(UserCard userCard) {
        if (userCard.getCardNumber() == null || userCard.getCardType() == null ||
                userCard.getExpirationDate() == null || userCard.getOwnerName() == null) {
            throw new IllegalArgumentException("Required fields are missing for the card");
        }
    }

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
        userCard.setActive(newUserCard.isActive());

        return userCardRepository.save(userCard);
    }

    public void deleteCardById(Long id) {
        Optional<UserCard> isUserCard = userCardRepository.findById(id);
        if (isUserCard.isEmpty()) {
            throw new EntityNotFoundException("Card not found");
        }
        userCardRepository.delete(isUserCard.get());
    }
}
