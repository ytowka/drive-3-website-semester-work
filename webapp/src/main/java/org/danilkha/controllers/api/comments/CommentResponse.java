package org.danilkha.controllers.api.comments;

public record CommentResponse (
        String avatarUrl,
        String name,
        String date,
        String authorUrl,
        String text
){
}
