package com.example.CampusHub.controller;


import com.example.CampusHub.dto.ChatMessageRequest;
import com.example.CampusHub.model.ChatMessage;
import com.example.CampusHub.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // ✅ Send message
    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessageRequest request) {
        return chatService.sendMessage(
                request.getReceiverId(),
                request.getMessage()
        );
    }

    // ✅ Get chat history
    @GetMapping("/{userId}")
    public List<ChatMessage> getChat(@PathVariable String userId) {
        return chatService.getChatHistory(userId);
    }
}