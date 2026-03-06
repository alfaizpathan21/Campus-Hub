package com.example.CampusHub.dto;


import lombok.Data;

@Data
public class InteractionMessage {
    private String postId;
    private String userId;
    private String type; // LIKE or COMMENT
    private String comment;
}