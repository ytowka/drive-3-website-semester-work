package org.danilkha.dto;

import java.util.UUID;

public record TopicDto(
        UUID id,
        String name,
        String picture
) {
}
