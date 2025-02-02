package com.example.finostra.Repositories;

import com.example.finostra.Entity.User;
import com.example.finostra.Entity.UserCards.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    UserCard findByCardNumber(String cardNumber);
    List<UserCard> findByUser(User user);
}
