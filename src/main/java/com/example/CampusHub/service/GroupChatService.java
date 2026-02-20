package com.example.CampusHub.service;


import com.example.CampusHub.model.Group;
import com.example.CampusHub.model.GroupMessage;
import com.example.CampusHub.repository.GroupMessageRepository;
import com.example.CampusHub.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupChatService {

    private final GroupRepository groupRepository;
    private final GroupMessageRepository messageRepository;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    // ✅ send group message
    public GroupMessage sendMessage(String groupId, String text) {

        String senderId = userService.getCurrentUser().getId();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        GroupMessage message = GroupMessage.builder()
                .groupId(groupId)
                .senderId(senderId)
                .message(text)
                .timestamp(LocalDateTime.now())
                .build();

        GroupMessage saved = messageRepository.save(message);

        // 🔥 push to all members
        for (String memberId : group.getMembers()) {
            messagingTemplate.convertAndSendToUser(
                    memberId,
                    "/queue/group",
                    saved
            );
        }

        return saved;
    }

    // ✅ history
    public List<GroupMessage> getMessages(String groupId) {
        return messageRepository.findByGroupIdOrderByTimestampAsc(groupId);
    }
}