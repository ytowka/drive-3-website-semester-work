package org.danilkha.dto;

import java.util.Date;
import java.util.UUID;

public record CommentDto(
        UUID id,
        UserDto userDto,
        UUID postId,
        UUID replyingId,
        Date date,
        String text
) {
}
