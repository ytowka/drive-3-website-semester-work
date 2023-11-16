package org.danilkha.entities;

import org.example.orm.ColumnInfo;
import org.example.orm.Entity;

import java.util.UUID;

@Entity(tableName = "subscriptions")
public record SubscriptionEntity(
        @ColumnInfo(name = "from_user_id") UUID from,
        @ColumnInfo(name = "to_user_id") UUID to
) {
}
