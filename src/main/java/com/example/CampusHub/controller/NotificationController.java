package com.example.CampusHub.controller;



import com.example.CampusHub.model.Notification;
import com.example.CampusHub.service.NotificationService;
import com.example.CampusHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    // ✅ Get my notifications
    @GetMapping
    public List<Notification> getMyNotifications() {
        return notificationService
                .getUserNotifications(userService.getCurrentUser().getId());
    }

    // ✅ Mark read
    @PutMapping("/{id}/read")
    public String markRead(@PathVariable String id) {
        notificationService.markAsRead(id);
        return "Notification marked as read";
    }
}