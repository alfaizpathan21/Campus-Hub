package com.example.CampusHub.service;


import com.example.CampusHub.enums.NotificationType;
import com.example.CampusHub.exception.UserNotFoundException;
import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final NotificationService notificationService;

    private final UserRepository userRepository;

    // ✅ Get currently logged-in user
    public User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    // ✅ Get user by ID
    public User getUserById(String userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    // ✅ Update profile
    public User updateProfile(User updatedUser) {

        User existingUser = getCurrentUser();

        existingUser.setName(updatedUser.getName());
        existingUser.setDepartment(updatedUser.getDepartment());
        existingUser.setSection(updatedUser.getSection());
        existingUser.setMobile(updatedUser.getMobile());

        return userRepository.save(existingUser);
    }

    public String followUser(String targetUserId) {

        User currentUser = getCurrentUser();

        if (currentUser.getId().equals(targetUserId)) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() ->
                        new UserNotFoundException("Target user not found"));

        // Prevent duplicate follow
        if (currentUser.getFollowing().contains(targetUserId)) {
            return "Already following this user";
        }

        currentUser.getFollowing().add(targetUserId);
        targetUser.getFollowers().add(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(targetUser);

        notificationService.createNotification(
                currentUser.getId(),
                targetUser.getId(),
                NotificationType.FOLLOW,
                "started following you"
        );
        return "User followed successfully";
    }
    public String unfollowUser(String targetUserId) {

        User currentUser = getCurrentUser();

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() ->
                        new UserNotFoundException("Target user not found"));

        currentUser.getFollowing().remove(targetUserId);
        targetUser.getFollowers().remove(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(targetUser);

        return "User unfollowed successfully";
    }

    public List<User> getFollowers(String userId) {

        User user = getUserById(userId);

        return user.getFollowers().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException("Follower not found")))
                .toList();
    }
    public List<User> getFollowing(String userId) {

        User user = getUserById(userId);

        return user.getFollowing().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() ->
                                new UserNotFoundException("Following user not found")))
                .toList();
    }


}
