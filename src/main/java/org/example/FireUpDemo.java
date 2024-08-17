package org.example;

import org.apache.spark.sql.Row;

public class FireUpDemo {
    public static void main(String[] args) {
        FireUpApplicationBuilder fireAppApplicationName = FireUpApplicationBuilder.create()
                .appName("AppName")
                .args(args)
                .<Row, Row>etl()
                .source((injector, argv) -> {
                    return null;
                })
                .build();

    }
}