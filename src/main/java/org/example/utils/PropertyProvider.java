package org.example.utils;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyProvider {

    private static final Properties properties = new Properties();


    static {
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties(){

        return new Properties(properties);
    }
}
