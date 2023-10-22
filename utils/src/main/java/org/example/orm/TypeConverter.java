package org.example.orm;

public abstract class TypeConverter<O, D> {

    private final Class<O> objectType;
    private final Class<D> databaseType;

     public TypeConverter(Class<O> objectType, Class<D> databaseType){
         this.objectType = objectType;
         this.databaseType = databaseType;
     }

    public Class<O> getObjectType() {
        return objectType;
    }

    public Class<D> getDatabaseType() {
        return databaseType;
    }

    public abstract D toDatabaseType(O object);

    public abstract O toObject(D databaseObject);

}

