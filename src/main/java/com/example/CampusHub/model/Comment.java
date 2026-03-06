package com.example.CampusHub.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private String userId;
    private String text;
    private LocalDateTime createdAt;
    public Comment(String userId, String text) {
        this.userId = userId;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }
}