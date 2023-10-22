package org.danilkha.entities;

import java.util.Date;
import java.util.UUID;

public record ConfirmationCodeEntity(
        UUID userId,
        int code,
        Date creationTime
) {
}
