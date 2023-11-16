package org.danilkha.entities.typeconverters;

import org.example.orm.TypeConverter;

import java.sql.Timestamp;
import java.util.Date;

public class TimestampTypeConverter extends TypeConverter<Date, Timestamp> {
    public TimestampTypeConverter() {
        super(Date.class, Timestamp.class);
    }

    @Override
    public Timestamp toDatabaseType(Date object) {
        return new Timestamp(object.getTime());
    }

    @Override
    public Date toObject(Timestamp databaseObject) {
        return new Date(databaseObject.getTime());
    }
}
