package com.example.CampusHub.service;

import com.example.CampusHub.enums.UserRole;
import com.example.CampusHub.model.Post;
import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.PostRepository;
import com.example.CampusHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // ✅ get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ update role
    public String updateUserRole(String userId, String role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(UserRole.valueOf(role.toUpperCase()));
        userRepository.save(user);

        return "User role updated successfully";
    }

    // ✅ delete user
    public String deleteUser(String userId) {

        userRepository.deleteById(userId);
        return "User deleted successfully";
    }

    // ✅ delete post
    public String deletePost(String postId) {

        postRepository.deleteById(postId);
        return "Post deleted successfully";
    }

    // ✅ get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // ✅ dashboard stats
    public Map<String, Long> getDashboardStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("totalUsers", userRepository.count());
        stats.put("totalPosts", postRepository.count());

        return stats;
    }
}