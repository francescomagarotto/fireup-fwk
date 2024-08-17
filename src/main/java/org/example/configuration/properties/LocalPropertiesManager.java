package org.example.configuration.properties;

import com.google.common.base.Preconditions;

public class LocalPropertiesManager implements PropertiesManager {

    @Override
    public Object get(String key) {
        Preconditions.checkNotNull(key);
        return System.getProperty(key);
    }
}
