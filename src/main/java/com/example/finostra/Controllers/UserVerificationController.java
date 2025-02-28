package com.example.finostra.Controllers;


import com.example.finostra.Entity.Requests.Email.UserEmailVerificationRequest;
import com.example.finostra.Entity.Requests.Email.UserEmailRegistrationRequest;
import com.example.finostra.Entity.Requests.Verification.UserPhoneNumberRegistrationRequest;
import com.example.finostra.Entity.Requests.Verification.UserPhoneNumberVerificationRequest;
import com.example.finostra.Services.EmailService.EmailService;
import com.example.finostra.Services.Sms.SmsService;
import com.example.finostra.Services.User.UserService;
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
@RequestMapping("/api/v1/user/verification")
public class UserVerificationController {

    private final UserService userService;

    private final SmsService smsService;
    private final EmailService emailService;


    @Autowired
    public UserVerificationController(UserService userService, SmsService smsService, EmailService emailService) {
        this.userService = userService;
        this.smsService = smsService;
        this.emailService = emailService;
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
            @RequestBody @Valid UserPhoneNumberVerificationRequest request
    )
    {
        String storedCode = smsService.fetchConfirmationCode(request.getPhoneNumber());
        if (storedCode != null && storedCode.equals(request.getConfirmationCode())) {
            smsService.eraseConfirmationCachedCode(request.getConfirmationCode());
            //TODO : UserInfo creation
            return ResponseEntity.ok("Phone number verified successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired confirmation code");
    }


    @PostMapping("/email/register")
    @Transactional
    public ResponseEntity<String> registerEmail(
            @RequestBody @Valid UserEmailRegistrationRequest request
    )
    {
        emailService.sendEmailVerificationCode(request.getEmail());

        return ResponseEntity.ok("Confirmation code was sent successfully");
    }


    @PostMapping("/email/verify")
    @Transactional
    public ResponseEntity<String> verifyEmail(
            @RequestBody @Valid UserEmailVerificationRequest request
    )
    {
        String storedCode = emailService.fetchConfirmationCode(request.getEmail());
        if (storedCode != null && storedCode.equals(request.getConfirmationCode())) {
            emailService.eraseConfirmationCachedCode(request.getConfirmationCode());
            //TODO : UserInfo modification
            return ResponseEntity.ok("Email verified successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired confirmation code");
    }











}
