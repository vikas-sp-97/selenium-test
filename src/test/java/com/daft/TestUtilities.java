package com.daft;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class to add the properties
 * that would be included in config.properties file
 * */
public class TestUtilities {

    // creating local property variable
    private Properties properties;

    public TestUtilities() {
        loadProperties();
    }

    // Getter method to access the properties
    public Properties getProperties() {
        return properties;
    }

    // method to add the properties defined in config.properties to properties
    private void loadProperties() {
        properties = new Properties();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/config.properties");
            if(inputStream != null){
                properties.load(inputStream);
                inputStream.close();
            }else {
                throw new IOException("Config file not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
