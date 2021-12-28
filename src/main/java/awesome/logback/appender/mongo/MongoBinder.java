package awesome.logback.appender.mongo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Getter;

/**
 * 
 * @author awesome
 */
public class MongoBinder {

    private static MongoBinder SINGLETON = new MongoBinder();

    static {
        SINGLETON.init();
    }

    static void reset() {
        SINGLETON = new MongoBinder();
        SINGLETON.init();
    }
    
    public static MongoBinder getSingleton() {
        return SINGLETON;
    }

    @Getter
    private MongoAddapter mongoAddapterImpl;

    private MongoBinder() {
    }

    @SuppressWarnings("unchecked")
    void init() {
        String classImplStr = this.getClass().getPackage().getName() + ".MongoFactoyAddapterImpl";

        try {
            Class<MongoFactoyAddapter> mongoFactoyAddapterClass = (Class<MongoFactoyAddapter>) Class.forName(classImplStr);
            System.out.println(mongoFactoyAddapterClass);
            Method method = mongoFactoyAddapterClass.getMethod("instance");
            System.out.println(method);
            if(method == null) {
                System.err.println("method not found!");
                return;
            }
            MongoFactoyAddapter mongoFactoyAddapterImpl = (MongoFactoyAddapter) method.invoke(null);
            if(mongoFactoyAddapterImpl == null) {
                System.err.println("mongoFactoyAddapterImpl init failed!");
                return;
            }
            
            mongoAddapterImpl = mongoFactoyAddapterImpl.create();
            System.out.println("mongoAddapterImpl : " + mongoAddapterImpl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
