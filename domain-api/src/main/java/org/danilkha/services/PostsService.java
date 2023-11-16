package org.danilkha.services;

import org.danilkha.dto.CommentDto;
import org.danilkha.dto.PostDto;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface PostsService {

    List<PostDto> getUserFeed(UUID userId, int page);

    List<PostDto> getPostsByTopic(UUID topicId);

    PostDto getPostById(UUID postId);

    List<PostDto> getUserPosts(UUID id);

    UUID writeNewPost(InputStream picture, String fileName, UUID authorId, String text, UUID topicId);

    void changeLikeState(UUID postId, UUID userId, boolean isLiked);

    List<UUID> getPostLikes(UUID postId);

    List<CommentDto> getPostComments(UUID postId);

    void addComment(UUID postId, UUID userId, String text, UUID replyingId);
}
