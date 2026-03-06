package com.example.CampusHub.dto;


import lombok.Data;

@Data
public class GroupMessageRequest {

    private String groupId;
    private String message;
}