package org.danilkha.entities;

import java.util.Date;
import java.util.UUID;

public record PasswordResetLinkEntity(
        UUID userId,
        String link,
        Date creationTime
) {
}
