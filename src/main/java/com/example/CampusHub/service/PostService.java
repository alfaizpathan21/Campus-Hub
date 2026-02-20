package com.example.CampusHub.service;


import com.example.CampusHub.dto.CommentRequest;
import com.example.CampusHub.dto.PostRequest;
import com.example.CampusHub.enums.NotificationType;
import com.example.CampusHub.model.Comment;
import com.example.CampusHub.model.Post;
import com.example.CampusHub.model.User;
import com.example.CampusHub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ContentModerationService moderationService;

    private final UserService userService;
    private final NotificationService notificationService;


    // ✅ Create post
    @CacheEvict(value = "feed", allEntries = true)
    public Post createPost(PostRequest request) {

        moderationService.validateText(request.getCaption());

        User user = userService.getCurrentUser();

        Post post = Post.builder()
                .userId(user.getId())
                .mediaUrl(request.getMediaUrl())
                .caption(request.getCaption())
                .createdAt(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }

    // ✅ Like / Unlike post
    // ✅ Like / Unlike post
    public String toggleLike(String postId) {

        User user = userService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        if (post.getLikes().contains(user.getId())) {
            post.getLikes().remove(user.getId());
            postRepository.save(post);
            return "Post unliked";
        } else {
            post.getLikes().add(user.getId());
            postRepository.save(post);

            // 🔥 notify only when liked
            notificationService.createNotification(
                    user.getId(),
                    post.getUserId(),
                    NotificationType.LIKE,
                    "liked your post"
            );

            return "Post liked";
        }
    }


    // ✅ Add comment
    public Post addComment(String postId, CommentRequest request) {
        moderationService.validateText(request.getText());

        User user = userService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found"));

        Comment comment = Comment.builder()
                .userId(user.getId())
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .build();

        post.getComments().add(comment);

        notificationService.createNotification(
                user.getId(),
                post.getUserId(),
                NotificationType.COMMENT,
                "commented on your post"
        );


        return postRepository.save(post);
    }

    // ✅ Get feed
    @Cacheable(value = "feed")
    public List<Post> getFeed() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    // ✅ Get user posts
    public List<Post> getUserPosts(String userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

}