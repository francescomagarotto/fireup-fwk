package org.example.properties.hocon;

import com.typesafe.config.Config;

public interface HoconConfiguration {
    Config configuration() throws Exception;
}
