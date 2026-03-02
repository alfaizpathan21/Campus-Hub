package com.example.CampusHub.controller;

import com.example.CampusHub.model.Post;
import com.example.CampusHub.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userId}")
    public List<Post> getFeed(@PathVariable String userId) {
        return feedService.getFeed(userId);
    }
}