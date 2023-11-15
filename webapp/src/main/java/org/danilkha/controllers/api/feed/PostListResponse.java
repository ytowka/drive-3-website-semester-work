package org.danilkha.controllers.api.feed;

import java.util.List;

public record PostListResponse(
        int page,
        List<PostResponse> posts
) {
}
