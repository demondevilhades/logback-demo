package awesome.logback.appender;

import ch.qos.logback.core.FileAppender;

/**
 * 
 * @author awesome
 */
public class CustomFileAppender<E> extends FileAppender<E> {
    
    private String p1Str = "abc";
    private String p2Str = "123";
    
    public CustomFileAppender() {
        super();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void setFile(String file) {
        super.setFile(file);
        fileName = fileName.replaceFirst("P_1", p1Str).replaceFirst("P_2", p2Str);
    }
}
