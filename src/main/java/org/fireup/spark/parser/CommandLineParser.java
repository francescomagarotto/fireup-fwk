package org.fireup.spark.parser;

import java.io.Serializable;
import java.util.Map;

public interface CommandLineParser extends Serializable {
    Map<String, String> parse(String[] args);
}
