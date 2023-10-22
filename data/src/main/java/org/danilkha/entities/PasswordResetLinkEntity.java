package org.danilkha.entities;

import org.example.orm.ColumnInfo;
import org.example.orm.Entity;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "password_reset_links")
public record PasswordResetLinkEntity(
        @ColumnInfo(name = "account_id") UUID userId,
        @ColumnInfo(name = "link") String link,
        @ColumnInfo(name = "creation_time", autoGenerated = true) Date creationTime
) {
}