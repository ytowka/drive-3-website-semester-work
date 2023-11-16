package org.danilkha.entities;

import org.example.orm.ColumnInfo;
import org.example.orm.Entity;

import java.util.UUID;

@Entity(tableName = "post_likes")
public record LikeStatusEntity(
        @ColumnInfo(name = "post_id") UUID postId,
        @ColumnInfo(name = "user_id") UUID userId,
        @ColumnInfo(name = "is_liked") boolean isLiked
) {
}
