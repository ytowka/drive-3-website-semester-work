package org.danilkha.entities;

import org.danilkha.entities.typeconverters.TimestampTypeConverter;
import org.example.orm.ColumnInfo;
import org.example.orm.Entity;
import org.example.orm.TypeConverters;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "posts")
public record PostEntity(
    @ColumnInfo(name = "id", autoGenerated = true) UUID id,
    @TypeConverters(typeConverterClass = TimestampTypeConverter.class)
    @ColumnInfo(name = "datetime")
    Date datetime,
    @ColumnInfo(name = "author_id") UUID authorId,
    @ColumnInfo(name = "topic_id") UUID topicId,
    @ColumnInfo(name = "picture_url") String pictureUrl,
    @ColumnInfo(name = "content") String content
){}
