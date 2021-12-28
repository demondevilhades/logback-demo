package awesome.logback.log;

import org.slf4j.MDC;

import lombok.extern.slf4j.XSlf4j;

/**
 * 
 * @author awesome
 */
@XSlf4j
public class MDCTest {

    public void run() {

        MDC.put("mdc1", "test111");
        MDC.put("mdc2", "test222");
        
        log.info("test1");
        log.info("{}", "test2");
        log.info("test3 = {}", new MDCTest());
        log.error("testtest4", new RuntimeException("test4"));
        log.error("testtest5", new RuntimeException(new Exception("test5")));
        MDC.clear();
        log.error("testtest6", new RuntimeException(new Exception(new RuntimeException("test6"))));
        
    }
}
