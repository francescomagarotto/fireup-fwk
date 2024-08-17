package org.example;

import com.google.inject.Injector;
import org.apache.spark.sql.Dataset;

import java.util.Map;

public interface Source<I> {

    Dataset<I> source(Injector injector, Map<String, String> args);
}
