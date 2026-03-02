package com.example.CampusHub.controller;

import com.example.CampusHub.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{targetUserId}")
    public void follow(@PathVariable String targetUserId,
                       @RequestParam String currentUserId) {
        followService.followUser(currentUserId, targetUserId);
    }

    @DeleteMapping("/{targetUserId}")
    public void unfollow(@PathVariable String targetUserId,
                         @RequestParam String currentUserId) {
        followService.unfollowUser(currentUserId, targetUserId);
    }
}