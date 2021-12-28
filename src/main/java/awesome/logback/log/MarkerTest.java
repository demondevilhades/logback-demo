package awesome.logback.log;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import lombok.extern.slf4j.XSlf4j;

/**
 * 
 * @author awesome
 */
@XSlf4j
public class MarkerTest {
    
    private static final Marker marker = MarkerFactory.getMarker("marker_test");

    public void run() {
        log.info(marker, "test1");
    }
}
