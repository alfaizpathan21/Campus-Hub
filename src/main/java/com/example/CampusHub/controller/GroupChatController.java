package com.example.CampusHub.controller;


import com.example.CampusHub.dto.GroupMessageRequest;
import com.example.CampusHub.model.GroupMessage;
import com.example.CampusHub.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-chat")
@RequiredArgsConstructor
public class GroupChatController {

    private final GroupChatService groupChatService;

    @PostMapping("/send")
    public GroupMessage send(@RequestBody GroupMessageRequest request) {
        return groupChatService.sendMessage(
                request.getGroupId(),
                request.getMessage()
        );
    }

    @GetMapping("/{groupId}")
    public List<GroupMessage> history(@PathVariable String groupId) {
        return groupChatService.getMessages(groupId);
    }
}
