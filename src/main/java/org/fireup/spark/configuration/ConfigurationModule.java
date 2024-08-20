package org.fireup.spark.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import org.fireup.spark.provider.ConfigurationProvider;

import java.io.Serializable;

public class ConfigurationModule extends AbstractModule implements Serializable {

    private final String[] args;

    public ConfigurationModule(String[] args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        bind(String[].class).annotatedWith(Names.named("ApplicationArgs")).toInstance(this.args);
        bind(Config.class).toProvider(ConfigurationProvider.class).asEagerSingleton();
    }
}
