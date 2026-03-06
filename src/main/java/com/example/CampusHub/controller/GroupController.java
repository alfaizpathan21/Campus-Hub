package com.example.CampusHub.controller;


import com.example.CampusHub.dto.CreateGroupRequest;
import com.example.CampusHub.model.Group;
import com.example.CampusHub.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public Group createGroup(@RequestBody CreateGroupRequest request) {
        return groupService.createGroup(request);
    }
}