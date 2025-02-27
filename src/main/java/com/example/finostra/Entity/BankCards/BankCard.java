package com.example.finostra.Entity.BankCards;

import com.example.finostra.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Getter
@Service
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false, unique = true, length = 19)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false, unique = true, length = 34)
    private String IBAN;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
