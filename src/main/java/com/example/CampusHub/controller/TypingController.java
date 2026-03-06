package com.example.CampusHub.controller;


import com.example.CampusHub.dto.TypingRequest;
import com.example.CampusHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TypingController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    // 🔥 handle typing event
    @MessageMapping("/chat.typing")
    public void typing(TypingRequest request) {

        String senderId = userService.getCurrentUser().getId();

        Map<String, Object> payload = Map.of(
                "senderId", senderId,
                "typing", request.isTyping()
        );

        messagingTemplate.convertAndSendToUser(
                request.getReceiverId(),
                "/queue/typing",
                payload
        );
    }
}
