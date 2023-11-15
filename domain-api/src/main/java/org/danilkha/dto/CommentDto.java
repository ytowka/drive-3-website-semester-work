package org.danilkha.dto;

import java.util.Date;
import java.util.UUID;

public record CommentDto(
        UUID id,
        UUID userId,
        UUID postId,
        UUID replyingId,
        Date date,
        String text
) {
}
