package com.example.CampusHub.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {

    private String title;
    private String description;
    private String department;
    private LocalDateTime eventDate;
}