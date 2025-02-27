package com.example.finostra.Entity.DTO;

import com.example.finostra.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankCardDTO {

    private Long userId;

    private String ownerName;

    private String cardNumber;

    private LocalDate expiryDate;

    private String IBAN;
}
