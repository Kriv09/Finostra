package com.example.finostra.Entity.Requests.Verification;


import com.example.finostra.Validation.PhoneNumber.ValidPhoneNumber;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Slf4j
public class UserPhoneNumberRegistrationRequest {

    @ValidPhoneNumber
    private String phoneNumber;

}
