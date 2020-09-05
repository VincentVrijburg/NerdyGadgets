package com.nerdygadgets.monitoring.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    String result;
    InputStream inputStream;

    public String getPropertyValue(String name) throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            this.inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and assign it to the result.
            result = prop.getProperty(name);
        } finally {
            this.inputStream.close();
        }

        return result;
    }
}
