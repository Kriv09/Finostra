package Repositories;

import Entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    UserCard findByCardNumber(String cardNumber);
}
