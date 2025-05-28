package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtil {
    public static String getEnvUrl(String env) throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("config-" + env + ".properties"));
        return prop.getProperty("url");
    }
}