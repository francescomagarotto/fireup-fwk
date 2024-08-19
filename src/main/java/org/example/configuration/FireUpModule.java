package org.example.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import org.apache.spark.sql.SparkSession;
import org.example.provider.ConfigurationProvider;
import org.example.provider.SparkSessionProvider;

import java.io.Serializable;

public class FireUpModule extends AbstractModule implements Serializable {

    private final String appName;

    public FireUpModule(String appName) {
        this.appName = appName;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("appName")).toInstance(appName);
        bind(FireUpRunningContext.class).asEagerSingleton();
        bind(SparkSession.class).toProvider(SparkSessionProvider.class).asEagerSingleton();
        bind(Config.class).toProvider(ConfigurationProvider.class).asEagerSingleton();

    }
}