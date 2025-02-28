package com.example.finostra.Repositories.User.Card;

import com.example.finostra.Entity.User.User;
import com.example.finostra.Entity.User.UserCards.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    UserCard findByCardNumber(String cardNumber);
    List<UserCard> findByUser(User user);
}
