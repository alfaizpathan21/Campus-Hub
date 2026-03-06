package com.example.CampusHub.controller;


import com.example.CampusHub.model.User;
import com.example.CampusHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ Get current logged-in user
    @GetMapping("/me")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    // ✅ Update profile
    @PutMapping("/update")
    public User updateProfile(@RequestBody User user) {
        return userService.updateProfile(user);
    }
    @PostMapping("/follow/{userId}")
    public String followUser(@PathVariable String userId) {
        return userService.followUser(userId);
    }
    @PostMapping("/unfollow/{userId}")
    public String unfollowUser(@PathVariable String userId) {
        return userService.unfollowUser(userId);
    }

    @GetMapping("/{userId}/followers")
    public List<User> getFollowers(@PathVariable String userId) {
        return userService.getFollowers(userId);
    }



}