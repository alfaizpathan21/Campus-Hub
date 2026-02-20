package com.example.CampusHub.repository;


import com.example.CampusHub.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);

    List<Post> findAllByOrderByCreatedAtDesc();
}