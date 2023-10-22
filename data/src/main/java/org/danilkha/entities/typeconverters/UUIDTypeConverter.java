package org.danilkha.entities.typeconverters;

import org.example.orm.TypeConverter;

import java.util.UUID;

public class UUIDTypeConverter extends TypeConverter<UUID, String> {

    public UUIDTypeConverter(){
        super(UUID.class, String.class);

    }

    @Override
    public String toDatabaseType(UUID object) {
        return object.toString();
    }

    @Override
    public UUID toObject(String databaseObject) {
        return UUID.fromString(databaseObject);
    }
}
