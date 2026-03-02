package com.example.CampusHub.model;



import com.example.CampusHub.enums.UserRole;
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
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String rollNo;
    private String email;
    private String department;
    private String section;
    private String mobile;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean verified;

    private List<String> followers = new ArrayList<>();
    private List<String> following = new ArrayList<>();

    private LocalDateTime createdAt;
}