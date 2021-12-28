package awesome.logback;

import awesome.logback.log.LogTest;
import awesome.logback.util.Config;

/**
 * 
 * @author awesome
 */
public class App {

    public static void main(String[] args) throws Exception {
        Config.init();
        new LogTest().run();

//        new MarkerTest().run();
        
//        new MDCTest().run();

//        new ProfilerTest().run();
    }
}
