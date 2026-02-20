package com.example.CampusHub.controller;


import com.example.CampusHub.dto.CommentRequest;
import com.example.CampusHub.dto.PostRequest;
import com.example.CampusHub.model.Post;
import com.example.CampusHub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // ✅ Create post
    @PostMapping
    public Post createPost(@RequestBody PostRequest request) {
        return postService.createPost(request);
    }

    // ✅ Like/unlike
    @PostMapping("/{postId}/like")
    public String toggleLike(@PathVariable String postId) {
        return postService.toggleLike(postId);
    }

    // ✅ Comment
    @PostMapping("/{postId}/comment")
    public Post addComment(@PathVariable String postId,
                           @RequestBody CommentRequest request) {
        return postService.addComment(postId, request);
    }

    // ✅ Feed
    @GetMapping("/feed")
    public List<Post> getFeed() {
        return postService.getFeed();
    }

    // ✅ User posts
    @GetMapping("/user/{userId}")
    public List<Post> getUserPosts(@PathVariable String userId) {
        return postService.getUserPosts(userId);
    }
}