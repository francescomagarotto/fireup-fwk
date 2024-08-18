package org.example.configuration.properties;

public interface PropertiesManager {
    Object get(String key);

    default Integer getAsInteger(String key) {
        return (Integer) get(key);
    }

    default Long getAsLong(String key) {
        return (Long) get(key);
    }

    default String getAsString(String key) {
        return (String) get(key);
    }
}
