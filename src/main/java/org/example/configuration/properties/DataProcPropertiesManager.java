package org.example.configuration.properties;

import com.google.common.base.Preconditions;

public class DataProcPropertiesManager implements PropertiesManager {
    private static final String PROPERTY_PREFIX = "spark.executorEnv.";

    @Override
    public Object get(String key) {
        Preconditions.checkNotNull(key);
        if (key.startsWith(PROPERTY_PREFIX)) {
            return System.getProperty(key);
        } else {
            return System.getenv(PROPERTY_PREFIX + key);
        }
    }
}
