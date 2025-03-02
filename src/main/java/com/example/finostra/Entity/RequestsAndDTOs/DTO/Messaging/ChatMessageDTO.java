package com.example.finostra.Entity.DTO.Messaging;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatMessageDTO {
    private String sender;
    private String content;
    private MessageType type;


}
