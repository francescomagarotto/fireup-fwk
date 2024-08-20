package org.example.provider;


import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.example.exception.ConfigurationMergeException;
import org.example.guice.SerializableProvider;
import org.example.parser.BaseCommandLineParser;
import org.example.parser.CommandLineParser;
import org.example.properties.hocon.ApplicationHoconConfiguration;
import org.example.properties.hocon.HoconConfiguration;

import java.util.List;
import java.util.Map;

public class ConfigurationProvider implements SerializableProvider<Config> {
    private final String[] args;

    @Inject
    public ConfigurationProvider(@Named("ApplicationArgs") String[] args) {
        this.args = args;
    }

    @Override
    public Config get() {
        List<HoconConfiguration> hoconConfigurations =
                List.of(getConfigFromApplicationArgs(),
                        new ApplicationHoconConfiguration());
        Config mergedConfiguration = ConfigFactory.empty();
        for (HoconConfiguration hoconConfiguration : hoconConfigurations) {
            try {
                mergedConfiguration = mergedConfiguration.withFallback(hoconConfiguration.configuration());
            } catch (Exception e) {
                throw new ConfigurationMergeException(e);
            }
        }
        return mergedConfiguration;
    }

    private HoconConfiguration getConfigFromApplicationArgs() {
        CommandLineParser commandLineParser = new BaseCommandLineParser();
        Map<String, String> applicationArgsMap = commandLineParser.parse(args);
        return () -> ConfigFactory.parseMap(applicationArgsMap);
    }
}
