package org.danilkha.dto;

import java.util.Date;
import java.util.UUID;

public record PostDto(
        UUID id,
        Date datetime,
        UUID authorId,
        UUID topicId,
        String picture,
        String content
) {

}
