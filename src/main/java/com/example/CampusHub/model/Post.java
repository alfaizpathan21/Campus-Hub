package com.example.CampusHub.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private String mediaUrl; // image/video

    private String caption;

    private List<String> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;
}