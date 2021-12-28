package awesome.logback.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author awesome
 */
public class Config {
    
    private static final Properties properties = new Properties();

    public static void init() throws Exception {
        System.out.println("Config.init start");
        try (FileInputStream fis = new FileInputStream(ResourcesUtils.getResource("app.properties").getFile());
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);) {
            properties.load(isr);
            System.out.println("Config.init end");
        }
    }

    public static String get(String key) {
        String property = properties.getProperty(key);
        if (property == null) {
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                if (key.toUpperCase().equals(((String) entry.getKey()).toUpperCase())) {
                    if (property == null) {
                        property = (String) entry.getValue();
                    } else {
                        return null;
                    }
                }
            }
        }
        return property;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
