package org.fireup.spark.properties.hocon;

import com.typesafe.config.Config;

public interface HoconConfiguration {
    Config configuration() throws Exception;
}
