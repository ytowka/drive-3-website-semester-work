package org.danilkha.entities.typeconverters;

import org.example.orm.TypeConverter;

import java.util.Date;

public class DateTypeConverter extends TypeConverter<Date, java.sql.Date> {
    public DateTypeConverter() {
        super(Date.class, java.sql.Date.class);
    }

    @Override
    public java.sql.Date toDatabaseType(Date object) {
        return new java.sql.Date(object.getTime());
    }

    @Override
    public Date toObject(java.sql.Date databaseObject) {
        return databaseObject;
    }

}
