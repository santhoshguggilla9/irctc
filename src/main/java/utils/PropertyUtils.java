package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class PropertyUtils {

          private Properties properties;

        public PropertyUtils() {
            properties = new Properties();
            try {
                FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getProperty(String key) {
            return properties.getProperty(key);
        }

}
