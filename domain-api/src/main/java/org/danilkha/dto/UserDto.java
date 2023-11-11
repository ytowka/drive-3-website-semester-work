package org.danilkha.dto;

import java.util.Date;
import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String firstname,
        String surname,
        String email,
        String passwordHash,
        String avatarUri,
        Date registrationDate
){ }
