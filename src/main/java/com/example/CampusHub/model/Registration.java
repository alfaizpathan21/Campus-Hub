package com.example.CampusHub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "registrations")
public class Registration {

    @Id
    private String id;

    private String eventId;

    private String userId;

    private LocalDateTime registeredAt;
}
