package com.example.finostra.Repositories;

import com.example.finostra.Entity.BankCards.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    BankCard findByCardNumber(String cardNumber);
    BankCard findByIBAN(String IBAN);
    List<BankCard> findByUserId(Long userId);

    boolean existsByCardNumber(String cardNumber);
    boolean existsByIBAN(String IBAN);
}
