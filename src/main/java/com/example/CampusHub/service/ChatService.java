package com.example.CampusHub.service;


import com.example.CampusHub.model.ChatMessage;
import com.example.CampusHub.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ContentModerationService moderationService;

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    // ✅ Send message
    public ChatMessage sendMessage(String receiverId, String text) {
        moderationService.validateText(text);

        String senderId = userService.getCurrentUser().getId();

        ChatMessage message = ChatMessage.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .message(text)
                .timestamp(LocalDateTime.now())
                .build();

        ChatMessage saved = chatRepository.save(message);

        // 🔥 REAL-TIME PUSH
        messagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/messages",
                saved
        );

        return saved;
    }

    // ✅ Get chat history
    public List<ChatMessage> getChatHistory(String otherUserId) {

        String currentUserId = userService.getCurrentUser().getId();

        return chatRepository
                .findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                        currentUserId,
                        otherUserId,
                        currentUserId,
                        otherUserId
                );
    }
}