package org.danilkha.controllers.api.topic;

import java.util.UUID;

public record TopicResponse(
        UUID id,
        String name
) {
}
