package com.example.CampusHub.dto;


import lombok.Data;
import java.util.List;

@Data
public class CreateGroupRequest {

    private String name;
    private List<String> memberIds;
}