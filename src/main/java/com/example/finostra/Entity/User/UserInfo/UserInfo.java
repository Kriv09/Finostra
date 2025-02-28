package com.example.finostra.Entity.User.UserInfo;


import com.example.finostra.Entity.User.User;
import com.example.finostra.Validation.PhoneNumber.ValidPhoneNumber;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Entity
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;


    @Column(unique = true)
    private String email;

    private boolean isEmailConfirmed;
    private boolean isPhoneNumberConfirmed;

    @ValidPhoneNumber
    private String phoneNumber;

    @Column(updatable = false)
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
