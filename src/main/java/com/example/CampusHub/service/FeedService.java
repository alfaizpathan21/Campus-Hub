package com.example.CampusHub.service;

import com.example.CampusHub.model.Post;
import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.PostRepository;
import com.example.CampusHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getFeed(String userId) {

        User user = userRepository.findById(userId).orElseThrow();

        List<String> following = new ArrayList<>(user.getFollowing());
        following.add(userId); // include own posts

        return postRepository.findByUserIdInOrderByCreatedAtDesc(following);
    }
}