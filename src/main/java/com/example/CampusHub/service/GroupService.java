package com.example.CampusHub.service;


import com.example.CampusHub.dto.CreateGroupRequest;
import com.example.CampusHub.model.Group;
import com.example.CampusHub.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    // ✅ create group
    public Group createGroup(CreateGroupRequest request) {

        String creatorId = userService.getCurrentUser().getId();

        Group group = Group.builder()
                .name(request.getName())
                .createdBy(creatorId)
                .members(request.getMemberIds())
                .createdAt(LocalDateTime.now())
                .build();

        group.getMembers().add(creatorId);

        return groupRepository.save(group);
    }
}
