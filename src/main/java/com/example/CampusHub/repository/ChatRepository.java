package com.example.CampusHub.repository;



import com.example.CampusHub.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository
        extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            String senderId,
            String receiverId,
            String receiverId2,
            String senderId2
    );
}