package com.example.finostra.Controllers.Messaging;

import com.example.finostra.Entity.DTO.Messaging.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatMessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(
            @Payload ChatMessageDTO chatMessage
    )
    {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(
            @Payload ChatMessageDTO chatMessageDTO,
            SimpMessageHeaderAccessor headerAccessor
    )
    {
        headerAccessor.getSessionAttributes().put("username", chatMessageDTO.getSender());
        return chatMessageDTO;
    }
}
