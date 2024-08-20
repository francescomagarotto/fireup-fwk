package org.fireup.spark.exception;

public class ConfigurationMergeException extends RuntimeException {
    public ConfigurationMergeException(Exception e) {
        super(e);
    }
}
