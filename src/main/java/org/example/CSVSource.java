package org.example;

import com.google.inject.Injector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.example.configuration.properties.PropertiesManager;
import org.example.etl.Source;

import java.util.Map;

public class CSVSource implements Source<Row> {

    @Override
    public Dataset<Row> source(Injector injector, SparkSession sparkSession, Map<String, String> args) {
        PropertiesManager propertiesManager = injector.getInstance(PropertiesManager.class);
        return sparkSession.read().format("csv").load();
    }
}
