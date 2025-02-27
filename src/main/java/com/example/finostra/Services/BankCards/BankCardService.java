package com.example.finostra.Services.BankCards;

import com.example.finostra.Entity.BankCards.BankCard;
import com.example.finostra.Entity.Requests.BankCards.BankCardRequest;
import com.example.finostra.Entity.User;
import com.example.finostra.Exceptions.UserCardBadRequestException;
import com.example.finostra.Exceptions.UserCardNotFoundException;
import com.example.finostra.Exceptions.UserNotFoundException;
import com.example.finostra.Repositories.BankCardRepository;
import com.example.finostra.Repositories.UserRepository;
import com.example.finostra.Utils.BankCards.BankCardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankCardService {

    private final BankCardRepository bankCardRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public BankCardService(BankCardRepository bankCardRepository, UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.bankCardRepository = bankCardRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    // Temporary, it shouldn't return all cards due to privacy, I think
    public List<BankCard> fetchAllBankCards() {
        return bankCardRepository.findAll();
    }

    public BankCard fetchBankCardById(Long bankCardId) {
        if(bankCardId == null) {
            throw new UserCardBadRequestException("BankCardId cannot be null");
        }

        Optional<BankCard> bankCard = bankCardRepository.findById(bankCardId);
        if(bankCard.isEmpty()) {
            throw new UserCardNotFoundException("BankCard not found");
        }

        return bankCard.get();
    }

    public List<BankCard> fetchBankCardsByUserId(Long userId) {
        if(userId == null) {
            throw new UserCardBadRequestException("UserId cannot be null");
        }

        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        return bankCardRepository.findByUserId(userId);
    }

    public void createBankCard(BankCardRequest bankCardRequest) {
        validateBankCardRequest(bankCardRequest);

        Optional<User> user = userRepository.findById(bankCardRequest.getUserId());
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found with id " + bankCardRequest.getUserId());
        }

        var ownerName = bankCardRequest.getOwnerName();
        var cardNumber = BankCardUtils.generateCardNumber();
        var expirationDate = BankCardUtils.generateExpirationDate(5);
        var IBAN = BankCardUtils.generateIBAN(user.get().getId());
        var userForBankCard = user.get();

        do {
            if (bankCardRepository.existsByCardNumber(cardNumber)) {
                cardNumber = BankCardUtils.generateCardNumber();
            }

            if (bankCardRepository.existsByIBAN(IBAN)) {
                IBAN = BankCardUtils.generateIBAN(user.get().getId());
            }

        } while (bankCardRepository.existsByCardNumber(cardNumber)
                || bankCardRepository.existsByIBAN(IBAN));

        BankCard bankCard = BankCard.builder()
                .ownerName(ownerName)
                .cardNumber(cardNumber)
                .expiryDate(expirationDate)
                .IBAN(IBAN)
                .user(userForBankCard)
                .build();

        bankCardRepository.save(bankCard);
    }


    private void validateBankCardRequest(BankCardRequest bankCardRequest) {
        if(bankCardRequest == null) {
            throw new UserCardBadRequestException("Request body(BankCardRequest) cannot be null");
        } else if(bankCardRequest.getOwnerName() == null) {
            throw new UserCardBadRequestException("OwnerName cannot be null");
        } else if(bankCardRequest.getUserId() == null) {
            throw new UserCardBadRequestException("UserId cannot be null");
        } else if(bankCardRequest.getUserId() < 0) {
            throw new UserCardBadRequestException("UserId cannot be negative");
        }

    }

}
