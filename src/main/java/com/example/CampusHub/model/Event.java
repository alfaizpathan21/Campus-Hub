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
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String title;
    private String description;

    private String department;

    private LocalDateTime eventDate;

    private String createdBy;

    private List<String> registeredStudents = new ArrayList<>();

    private LocalDateTime createdAt;
}