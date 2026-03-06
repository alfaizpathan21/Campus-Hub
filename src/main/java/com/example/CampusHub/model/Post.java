package com.example.CampusHub.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    private String userId;

    private String mediaUrl;

    private String caption;

    private List<String> hashtags = new ArrayList<>();

    private List<String> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    private int shares = 0;
    private int views = 0;

    private LocalDateTime createdAt;
}