package org.example.provider;

import com.google.inject.Inject;
import org.example.configuration.FireUpRunningContext;
import org.example.guice.SerializableProvider;
import org.example.properties.DataProcPropertiesManager;
import org.example.properties.LocalPropertiesManager;
import org.example.properties.PropertiesManager;

public class ConfigurationManagerProvider implements SerializableProvider<PropertiesManager> {

    private final FireUpRunningContext context;

    @Inject
    public ConfigurationManagerProvider(FireUpRunningContext context) {
        this.context = context;
    }

    @Override
    public PropertiesManager get() {
        return context.isRunningOnDataproc()
                ? new DataProcPropertiesManager()
                : new LocalPropertiesManager();
    }
}
