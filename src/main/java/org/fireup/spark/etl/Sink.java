package org.fireup.spark.etl;

import com.google.inject.Injector;
import com.typesafe.config.Config;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

public interface Sink<O> extends Serializable {

    void flush(Dataset<O> dataset, Injector injector, SparkSession sparkSession, Config args);
}
