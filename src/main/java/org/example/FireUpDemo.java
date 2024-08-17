package org.example;

import org.apache.spark.sql.Row;

public class FireUpDemo {
    public static void main(String[] args) {
        FireUpApplicationBuilder.create()
                .appName("FireUpDemo")
                .args(args)
                .<Row, Row>etl()
                .source(new CSVSource())
                .toApplication()
                .run();
    }
}