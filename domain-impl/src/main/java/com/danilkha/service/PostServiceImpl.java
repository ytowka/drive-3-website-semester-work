package com.danilkha.service;

import org.danilkha.dto.CommentDto;
import org.danilkha.dto.PostDto;
import org.danilkha.services.PostsService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class PostServiceImpl implements PostsService {
    @Override
    public List<PostDto> getUserFeed(UUID userId, int from, int to) {
        return null;
    }

    @Override
    public List<PostDto> getPostsByTopic(UUID topicId) {
        return null;
    }

    @Override
    public PostDto getPostById(UUID postId) {
        return null;
    }

    @Override
    public List<PostDto> getUserPosts(UUID id) {
        return null;
    }

    @Override
    public void writeNewPost(InputStream picture, String fileName, String text, UUID topicId) {

    }

    @Override
    public void changeLikeState(UUID postId, UUID userId, boolean isLiked) {

    }

    @Override
    public List<UUID> getPostLikes(UUID postId) {
        return null;
    }

    @Override
    public List<CommentDto> getPostComments(UUID postId) {
        return null;
    }

    @Override
    public void addComment(UUID postId, UUID userId, String text, UUID replyingId) {

    }
}
