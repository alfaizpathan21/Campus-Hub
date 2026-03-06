package com.example.CampusHub.controller;

import com.example.CampusHub.model.Post;
import com.example.CampusHub.model.User;
import com.example.CampusHub.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ===============================
    // ✅ Get all users
    // ===============================
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // ===============================
    // ✅ Change user role
    // ===============================
    @PutMapping("/users/{userId}/role")
    public String updateUserRole(@PathVariable String userId,
                                 @RequestParam String role) {
        return adminService.updateUserRole(userId, role);
    }

    // ===============================
    // ✅ Delete user
    // ===============================
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return adminService.deleteUser(userId);
    }

    // ===============================
    // ✅ Delete post (moderation)
    // ===============================
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable String postId) {
        return adminService.deletePost(postId);
    }

    // ===============================
    // ✅ Get all posts (optional)
    // ===============================
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return adminService.getAllPosts();
    }

    // ===============================
    // ✅ Dashboard analytics
    // ===============================
    @GetMapping("/dashboard")
    public Map<String, Long> getDashboard() {
        return adminService.getDashboardStats();
    }
}