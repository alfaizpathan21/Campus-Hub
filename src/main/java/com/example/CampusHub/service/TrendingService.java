package com.example.CampusHub.service;

import com.example.CampusHub.model.Post;
import com.example.CampusHub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendingService {

    private final PostRepository postRepository;

    public List<Post> getTrendingPosts() {

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();

        return posts.stream()
                .sorted(Comparator.comparingDouble(this::calculateScore).reversed())
                .limit(20)
                .collect(Collectors.toList());
    }

    private double calculateScore(Post post) {

        long hours = ChronoUnit.HOURS.between(post.getCreatedAt(), LocalDateTime.now());
        if (hours <= 0) hours = 1;

        double engagement =
                post.getLikes().size() * 2 +
                        post.getComments().size() * 3 +
                        post.getShares() * 4 +
                        post.getViews();

        return engagement / hours;
    }
}