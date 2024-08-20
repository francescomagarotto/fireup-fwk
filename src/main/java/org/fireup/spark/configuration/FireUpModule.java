package org.fireup.spark.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.spark.sql.SparkSession;
import org.fireup.spark.provider.SparkSessionProvider;

import java.io.Serializable;

public class FireUpModule extends AbstractModule implements Serializable {

    private final String appName;
    public FireUpModule(String appName) {
        this.appName = appName;
    }

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("AppName")).toInstance(appName);
        bind(FireUpRunningContext.class).asEagerSingleton();
        bind(SparkSession.class).toProvider(SparkSessionProvider.class).asEagerSingleton();
    }
}