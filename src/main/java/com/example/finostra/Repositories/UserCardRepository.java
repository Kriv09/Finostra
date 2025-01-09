package com.example.finostra.Repositories;

import com.example.finostra.Entity.User;
import com.example.finostra.Entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    UserCard findByCardNumber(String cardNumber);
    List<UserCard> findByUser(User user);

    Optional<UserCard> getByCardNumber(String cardNumber);

}
