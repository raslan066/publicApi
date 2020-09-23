package apple.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            String pwd = System.getProperty("user.dir");
            FileInputStream inputStream = new FileInputStream(pwd + "/src/main/resources/conf.properties");
            properties.load(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}