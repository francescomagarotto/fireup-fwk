package org.example;

import com.google.inject.Injector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.provider.ConfigurationProvider;
import org.example.etl.Source;

import java.util.Map;

public class CSVSource implements Source<Row> {

    @Override
    public Dataset<Row> source(Injector injector, SparkSession sparkSession, Map<String, String> args) {
        ConfigurationProvider configurationProvider = injector.getInstance(ConfigurationProvider.class);
        return sparkSession.read().format("csv").load();
    }
}
