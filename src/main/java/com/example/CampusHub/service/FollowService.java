package com.example.CampusHub.service;

import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;

    public void followUser(String currentUserId, String targetUserId) {

        User currentUser = userRepository.findById(currentUserId).orElseThrow();
        User targetUser = userRepository.findById(targetUserId).orElseThrow();

        currentUser.getFollowing().add(targetUserId);
        targetUser.getFollowers().add(currentUserId);

        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }

    public void unfollowUser(String currentUserId, String targetUserId) {

        User currentUser = userRepository.findById(currentUserId).orElseThrow();
        User targetUser = userRepository.findById(targetUserId).orElseThrow();

        currentUser.getFollowing().remove(targetUserId);
        targetUser.getFollowers().remove(currentUserId);

        userRepository.save(currentUser);
        userRepository.save(targetUser);
    }
}