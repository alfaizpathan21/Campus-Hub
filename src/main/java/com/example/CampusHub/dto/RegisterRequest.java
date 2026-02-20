package com.example.CampusHub.dto;


import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String rollNo;
    private String email;
    private String department;
    private String section;
    private String mobile;
    private String password;
}