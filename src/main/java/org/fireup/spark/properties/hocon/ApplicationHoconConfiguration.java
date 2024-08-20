package org.fireup.spark.properties.hocon;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ApplicationHoconConfiguration implements HoconConfiguration {
    @Override
    public Config configuration() throws Exception {
        String fireUpConfigurationPath = System.getProperty("FIREUP_CONFIGURATION", "application.conf");
        return ConfigFactory.parseFile(loadFile(fireUpConfigurationPath));
    }

    private File loadFile(String path) throws IOException, URISyntaxException {
        URL resource = ApplicationHoconConfiguration.class.getClassLoader().getResource(path);
        if (resource != null) {
            return new File(resource.toURI());
        }

        File file = new File(path);
        if (file.exists()) {
            return file;
        }

        throw new FileNotFoundException(path);
    }

}
