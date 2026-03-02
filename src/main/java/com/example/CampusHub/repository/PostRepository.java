package com.example.CampusHub.repository;

import com.example.CampusHub.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    // User Profile Posts
    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);

    // Latest Posts (Home Feed)
    List<Post> findAllByOrderByCreatedAtDesc();

    // Recent posts (last 24 hours)
    List<Post> findByCreatedAtAfter(LocalDateTime time);

    // Hashtag search
    List<Post> findByHashtagsContaining(String hashtag);

    List<Post> findByUserIdInOrderByCreatedAtDesc(List<String> userIds);

}