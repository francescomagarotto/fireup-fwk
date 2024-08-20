package org.fireup.spark;

import com.google.inject.Injector;
import com.typesafe.config.Config;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.fireup.spark.provider.ConfigurationProvider;
import org.fireup.spark.etl.Source;

public class CSVSource implements Source<Row> {

    @Override
    public Dataset<Row> source(Injector injector, SparkSession sparkSession, Config config) {
        ConfigurationProvider configurationProvider = injector.getInstance(ConfigurationProvider.class);
        return sparkSession.read().format("csv").load();
    }
}
