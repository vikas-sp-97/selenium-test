package com.daft;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestUtilities {


    private Properties properties;

    public TestUtilities() {
        loadProperties();
    }

    public Properties getProperties() {
        return properties;
    }

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
