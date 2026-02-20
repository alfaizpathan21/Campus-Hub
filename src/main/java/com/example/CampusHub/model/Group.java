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
@Document(collection = "groups")
public class Group {

    @Id
    private String id;

    private String name;

    private String createdBy;

    private List<String> members = new ArrayList<>();

    private LocalDateTime createdAt;
}