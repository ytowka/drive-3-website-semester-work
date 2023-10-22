package org.example.orm;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TypeConverters {
    Class<? extends TypeConverter<?,?>> typeConverterClass();
}
