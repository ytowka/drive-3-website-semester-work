package org.danilkha.controllers.api.users;

public record UserResponse(
        String name,
        String fullname,
        String avatarUrl,
        String profileUrl
) {
}
