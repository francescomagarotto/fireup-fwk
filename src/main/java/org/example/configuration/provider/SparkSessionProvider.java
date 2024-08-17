package org.example.configuration.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.spark.sql.SparkSession;
import org.example.configuration.FireUpRunningContext;


public class SparkSessionProvider implements Provider<SparkSession> {

    private final String appName;
    private final FireUpRunningContext fireUpRunningContext;

    @Inject
    public SparkSessionProvider(String appName, FireUpRunningContext fireUpRunningContext) {
        this.appName = appName;
        this.fireUpRunningContext = fireUpRunningContext;
    }

    @Override
    public SparkSession get() {
        SparkSession.Builder sparkSessionBuilder = SparkSession.builder().appName(appName);
        if (!fireUpRunningContext.isRunningOnDataproc()) {
            sparkSessionBuilder.master("local");
        }
        return sparkSessionBuilder.getOrCreate();
    }

}

