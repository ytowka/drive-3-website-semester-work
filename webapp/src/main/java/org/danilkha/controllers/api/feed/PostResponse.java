package org.danilkha.controllers.api.feed;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String author,
        String authorUrl,
        String avatarUrl,
        String date,
        String image,
        String text,
        int likeCount,
        boolean isLiked,
        String topicUrl,
        String topicName
) {
}
