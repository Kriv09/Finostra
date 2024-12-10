package Controllers;


import Entity.DTO.UserRegistrationDto;
import Services.CustomUserDetailsService;
import Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        userService.registerNewUser(registrationDto);
        return ResponseEntity.ok("User registered successfully");
    }

}
