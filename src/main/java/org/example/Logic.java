package org.example;

import com.google.inject.Injector;
import org.apache.spark.sql.Dataset;

import java.util.Map;

public interface Logic<I, O> {
    Dataset<O> transform(Dataset<I> input, Injector injector, Map<String, String> args);
}
