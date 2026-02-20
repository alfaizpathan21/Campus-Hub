package com.example.CampusHub.dto;


import lombok.Data;

@Data
public class ChatMessageRequest {

    private String receiverId;
    private String message;
}