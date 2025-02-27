package com.example.finostra.Entity.Requests.BankCards;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankCardRequest {
    private Long userId;
    private String ownerName;
}
