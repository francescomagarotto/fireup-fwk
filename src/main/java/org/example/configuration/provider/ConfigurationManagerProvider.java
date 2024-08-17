package org.example.configuration.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.example.configuration.properties.PropertiesManager;
import org.example.configuration.properties.DataProcPropertiesManager;
import org.example.configuration.FireUpRunningContext;
import org.example.configuration.properties.LocalPropertiesManager;

public class ConfigurationManagerProvider implements Provider<PropertiesManager> {

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
