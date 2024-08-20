package org.example.etl;

import com.google.inject.Injector;
import com.typesafe.config.Config;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

public interface Logic<I, O> extends Serializable {
    Dataset<O> logic(Dataset<I> input, SparkSession sparkSession, Injector injector, Config args);
}
