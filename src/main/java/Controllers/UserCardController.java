package Controllers;

import Entity.UserCard;
import Services.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userCard")
public class UserCardController {
    @Autowired
    private UserCardService userCardService;

    public UserCardController(UserCardService userCardService) {
        this.userCardService = userCardService;
    }

    @PostMapping
    public ResponseEntity<String> addUserCard(@RequestBody UserCard userCard) {
        userCardService.addCard(userCard);
        return ResponseEntity.ok("User Card Added");
    }

}
