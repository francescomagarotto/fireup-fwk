package org.fireup.spark.provider;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import org.apache.spark.sql.SparkSession;
import org.fireup.spark.configuration.FireUpRunningContext;
import org.fireup.spark.guice.SerializableProvider;

import java.util.Map;


public class SparkSessionProvider implements SerializableProvider<SparkSession> {

    private final String appName;
    private final FireUpRunningContext fireUpRunningContext;
    private final Config config;

    @Inject
    public SparkSessionProvider(@Named("AppName") String appName,
                                FireUpRunningContext fireUpRunningContext,
                                Config config) {
        this.appName = appName;
        this.fireUpRunningContext = fireUpRunningContext;
        this.config = config;
    }

    @Override
    public SparkSession get() {
        SparkSession.Builder sparkSessionBuilder = SparkSession.builder().appName(appName);
        if (!fireUpRunningContext.isRunningOnDataproc()) {
            sparkSessionBuilder.master("local");
        }
        addSparkConfiguration(sparkSessionBuilder);
        return sparkSessionBuilder.getOrCreate();
    }

    private void addSparkConfiguration(SparkSession.Builder sparkSessionBuilder) {
        Config sparkConfig = config.getConfig("spark-config");
        for (Map.Entry<String, ConfigValue> entry : sparkConfig.entrySet()) {
            sparkSessionBuilder.config(entry.getKey(), entry.getValue().render());
        }
    }

}

