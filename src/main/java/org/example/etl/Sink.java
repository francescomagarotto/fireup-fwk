package org.example.etl;

import com.google.inject.Injector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public interface Sink<O> {

    void flush(Dataset<O> dataset, Injector injector, SparkSession sparkSession, Map<String, String> args);
}
