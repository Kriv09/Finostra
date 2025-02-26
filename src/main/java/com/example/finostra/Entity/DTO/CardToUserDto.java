package com.example.finostra.Entity.DTO;

import com.example.finostra.Entity.UserCards.CardType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardToUserDto {

    private Long userId;

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private LocalDate expirationDate;

    private String ownerName;

    private boolean active;
}
