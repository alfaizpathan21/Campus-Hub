package com.example.CampusHub.controller;

import com.example.CampusHub.dto.InteractionMessage;
import com.example.CampusHub.model.Comment;
import com.example.CampusHub.model.Post;
import com.example.CampusHub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class InteractionWebSocketController {

    private final PostRepository postRepository;

    @MessageMapping("/interact")
    @SendTo("/topic/posts")
    public Post handleInteraction(InteractionMessage message) {

        Post post = postRepository.findById(message.getPostId()).orElseThrow();

        if ("LIKE".equals(message.getType())) {
            post.getLikes().add(message.getUserId());
        }

        if ("COMMENT".equals(message.getType())) {

            Comment comment = Comment.builder()
                    .userId(message.getUserId())
                    .text(message.getComment())
                    .createdAt(LocalDateTime.now())
                    .build();

            post.getComments().add(comment);
        }

        return postRepository.save(post);
    }
}