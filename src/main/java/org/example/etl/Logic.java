package org.example.etl;

import com.google.inject.Injector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

public interface Logic<I, O> {
    Dataset<O> logic(Dataset<I> input, SparkSession sparkSession, Injector injector, Map<String, String> args);
}
