package awesome.logback.util;

import java.net.URL;

/**
 * 
 * @author awesome
 */
public class ResourcesUtils {

    public static String getResourceFile(String str) {
        return ResourcesUtils.class.getClassLoader().getResource(str).getFile();
    }

    public static URL getResource(String str) {
        return ResourcesUtils.class.getClassLoader().getResource(str);
    }
}
