package org.danilkha.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String name,
        String email,
        String avatarUri,
        boolean isEmailConfirmed
){ }
