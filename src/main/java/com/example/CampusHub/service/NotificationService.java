package com.example.CampusHub.service;

import com.example.CampusHub.enums.NotificationType;
import com.example.CampusHub.model.Notification;
import com.example.CampusHub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final SimpMessagingTemplate messagingTemplate;
    // ✅ Create notification
    public void createNotification(String senderId,
                                   String receiverId,
                                   NotificationType type,
                                   String message) {

        Notification notification = Notification.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .type(type)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        // 🔥 REAL-TIME PUSH
        messagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/notifications",
                saved
        );
    }

    // ✅ Get user notifications
    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository
                .findByReceiverIdOrderByCreatedAtDesc(userId);
    }

    // ✅ Mark as read
    public void markAsRead(String notificationId) {

        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
