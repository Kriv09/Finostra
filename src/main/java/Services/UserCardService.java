package Services;

import Entity.UserCard;
import Repositories.UserCardRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCardService {

    // Repository
    private final UserCardRepository userCardRepository;

    // Init
    public UserCardService(UserCardRepository userCardRepository) {
        this.userCardRepository = userCardRepository;
    }


    // USER CARD ADDING
    public UserCard addCard(UserCard userCard) {
        if(userCardRepository.findByCardNumber(userCard.getCardNumber()) != null) {
            throw new IllegalArgumentException("Card number already exists");
        }
        if(userCard.getCardType() == null) {
            throw new IllegalArgumentException("Card type is null");
        }
        if(userCard.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration Date is null");
        }
        if(userCard.getCardNumber() == null) {
            throw new IllegalArgumentException("Card number is null");
        }
        if(userCard.getOwnerName() == null) {
            throw new IllegalArgumentException("Owner name is null");
        }
        return userCardRepository.save(userCard);
    }


}
