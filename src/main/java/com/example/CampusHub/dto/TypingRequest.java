package com.example.CampusHub.dto;

import lombok.Data;

@Data
public class TypingRequest {

    private String receiverId;
    private boolean typing; // true = typing, false = stopped
}

