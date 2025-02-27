package com.example.finostra.Services.BankCards;

import com.example.finostra.Entity.BankCards.BankCard;
import com.example.finostra.Entity.User;
import com.example.finostra.Exceptions.UserCardBadRequestException;
import com.example.finostra.Exceptions.UserCardNotFoundException;
import com.example.finostra.Exceptions.UserNotFoundException;
import com.example.finostra.Repositories.BankCardRepository;
import com.example.finostra.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankCardService {

    private final BankCardRepository bankCardRepository;
    private final UserRepository userRepository;

    public BankCardService(BankCardRepository bankCardRepository, UserRepository userRepository) {
        this.bankCardRepository = bankCardRepository;
        this.userRepository = userRepository;
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

}
