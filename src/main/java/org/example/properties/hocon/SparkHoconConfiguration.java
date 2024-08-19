package org.example.properties.hocon;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SparkHoconConfiguration implements HoconConfiguration {

    private final SparkSession sparkSession;

    public SparkHoconConfiguration(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    @Override
    public Config configuration() {
        SparkContext sparkContext = this.sparkSession.sparkContext();
        SparkConf conf = sparkContext.getConf();
        Map<String, String> sparkPropertiesMap = Arrays.stream(conf.getAll()).collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
        return ConfigFactory.parseMap(sparkPropertiesMap);
    }
}
