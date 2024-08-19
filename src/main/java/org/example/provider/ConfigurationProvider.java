package org.example.provider;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.spark.sql.SparkSession;
import org.example.guice.SerializableProvider;
import org.example.properties.hocon.ApplicationHoconConfiguration;
import org.example.properties.hocon.HoconConfiguration;
import org.example.properties.hocon.SparkHoconConfiguration;

import java.util.List;

public class ConfigurationProvider implements SerializableProvider<Config> {
    private final SparkSession sparkSession;

    public ConfigurationProvider(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    @Override
    public Config get() {
        List<HoconConfiguration> hoconConfigurations =
                List.of(new SparkHoconConfiguration(sparkSession),
                        new ApplicationHoconConfiguration());
        Config mergedConfiguration = ConfigFactory.empty();
        for (HoconConfiguration hoconConfiguration : hoconConfigurations) {
            try {
                mergedConfiguration = mergedConfiguration.withFallback(hoconConfiguration.configuration());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return mergedConfiguration;
    }
}
