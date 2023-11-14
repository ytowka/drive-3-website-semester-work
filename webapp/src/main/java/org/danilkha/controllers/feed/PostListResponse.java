package org.danilkha.controllers.feed;

import java.util.List;

public record PostListResponse(
        int page,
        List<PostResponse> posts
) {
}
