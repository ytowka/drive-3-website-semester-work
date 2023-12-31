package org.danilkha.dto;

import java.util.Date;
import java.util.UUID;

public record PostDto(
        UUID id,
        Date datetime,
        UserDto author,
        TopicDto topic,
        String picture,
        String content
) { }
