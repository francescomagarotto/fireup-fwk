package org.example.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.spark.sql.SparkSession;
import org.example.configuration.properties.PropertiesManager;
import org.example.configuration.provider.ConfigurationManagerProvider;
import org.example.configuration.provider.SparkSessionProvider;

public class FireUpModule extends AbstractModule {

    private final String appName;

    public FireUpModule(String appName) {
        this.appName = appName;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("appName")).toInstance(appName);
        bind(FireUpRunningContext.class).to(FireUpRunningContext.class).asEagerSingleton();
        bind(PropertiesManager.class).toProvider(ConfigurationManagerProvider.class).asEagerSingleton();
        bind(SparkSession.class).toProvider(SparkSessionProvider.class).asEagerSingleton();
    }
}