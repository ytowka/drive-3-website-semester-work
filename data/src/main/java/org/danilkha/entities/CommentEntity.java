package org.danilkha.entities;

import org.danilkha.dto.UserDto;
import org.danilkha.entities.typeconverters.TimestampTypeConverter;
import org.example.orm.ColumnInfo;
import org.example.orm.Entity;
import org.example.orm.TypeConverters;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "comments")
public record CommentEntity(
        @ColumnInfo(name = "id", autoGenerated = true) UUID id,
        @ColumnInfo(name = "user_id") UUID userId,
        @ColumnInfo(name = "post_id") UUID postId,
        @ColumnInfo(name = "replying_id") UUID replyingId,
        @ColumnInfo(name = "date")
        @TypeConverters(typeConverterClass = TimestampTypeConverter.class)
        Date date,
        @ColumnInfo(name = "text") String text
) {
}
