package awesome.logback.log;

import java.util.Random;

import org.slf4j.profiler.Profiler;

import lombok.extern.slf4j.XSlf4j;

/**
 * 
 * @author awesome
 */
@XSlf4j
public class ProfilerTest {

    public void run() throws InterruptedException {
        log.debug("ProfilerTest");
        
        Random random = new Random();
        
        Profiler profiler = new Profiler("TEST");
        profiler.setLogger(log);
        
        profiler.start("A");
        Thread.sleep(random.nextInt(3) * 1000);
        
        profiler.start("B");
        Thread.sleep(random.nextInt(3) * 1000);
        
        profiler.start("C");
        Thread.sleep(random.nextInt(3) * 1000);
        
        profiler.stop().log();
    }
}
