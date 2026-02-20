package com.example.CampusHub.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "group_messages")
public class GroupMessage {

    @Id
    private String id;

    private String groupId;

    private String senderId;

    private String message;

    private LocalDateTime timestamp;
}
