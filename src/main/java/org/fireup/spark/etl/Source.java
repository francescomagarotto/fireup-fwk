package org.fireup.spark.etl;

import com.google.inject.Injector;
import com.typesafe.config.Config;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

public interface Source<I> extends Serializable {

    Dataset<I> source(Injector injector, SparkSession sparkSession, Config config);
}
