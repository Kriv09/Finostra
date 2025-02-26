package com.example.finostra.Controllers;


import com.example.finostra.Entity.Requests.Email.UserEmailRegistrationRequest;
import com.example.finostra.Entity.Requests.Verification.UserPhoneNumberRegistrationRequest;
import com.example.finostra.Entity.Requests.Verification.VerificationRequest;
import com.example.finostra.Services.Sms.SmsService;
import com.example.finostra.Services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final SmsService smsService;


    @Autowired
    public UserController(UserService userService, SmsService smsService) {
        this.userService = userService;
        this.smsService = smsService;
    }



    @PostMapping("/phoneNumber/register")
    @Transactional
    public ResponseEntity<String> registerPhoneNumber (
            @RequestBody @Valid UserPhoneNumberRegistrationRequest request
    )
    {
        smsService.sendConfirmationCode(request.getPhoneNumber());
        return ResponseEntity.ok("Confirmation code was sent successfully");
    }

    @PostMapping("/phoneNumber/verify")
    @Transactional
    public ResponseEntity<String> verifyConfirmationCode(
            @RequestBody @Valid  VerificationRequest request
    )
    {
        String storedCode = smsService.fetchConfirmationCode(request.getPhoneNumber());
        if (storedCode != null && storedCode.equals(request.getConfirmationCode())) {
            smsService.eraseConfirmationCachedCode(request.getConfirmationCode());
            return ResponseEntity.ok("Phone number verified successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired confirmation code");
    }



    @PostMapping("/email/register")
    public ResponseEntity<String> registerEmail(
            @RequestBody @Valid UserEmailRegistrationRequest request
    )
    {
        // TODO : Complete verification & confirmation via email

        return ResponseEntity.ok("Confirmation code was sent successfully");
    }







}
