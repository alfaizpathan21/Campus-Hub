package com.example.CampusHub.controller;

import com.example.CampusHub.model.Post;
import com.example.CampusHub.service.TrendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trending")
@RequiredArgsConstructor
public class TrendingController {

    private final TrendingService trendingService;

    @GetMapping
    public List<Post> trending() {
        return trendingService.getTrendingPosts();
    }
}