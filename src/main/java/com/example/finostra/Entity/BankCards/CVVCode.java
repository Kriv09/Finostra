package com.example.finostra.Entity.BankCards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@RedisHash(value = "CardSecurityCode")
public class CVVCode {

    @JsonIgnore
    private Long bankCardId;

    private Short cvv;

    private Long expiryTimeInSeconds;
}
