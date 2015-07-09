package mb.metrics;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * Helper Factory class for metrics.
 *
 * @author dhudson
 */
public class MetricsFactory {

    private static final MetricRegistry theRegistry = new MetricRegistry();

    /**
     * Create or return a meter with the given name.
     * 
     * The class should be the calling class, to prevent any conflicts.
     * 
     * @param clazz used as prefix to id
     * @param id to use
     * @return a meter
     */
    public static Meter getMeter(Class<?> clazz, String id) {
        return theRegistry.meter(getName(clazz,id));
    }
    
    /**
     * Create or return a timer with the given name.
     * 
     * The class should be the calling class, to prevent conflicts.
     * 
     * @param clazz used as a prefix to id
     * @param id to use
     * @return a timer
     */
    public static Timer getTimer(Class<?> clazz, String id) {
        return theRegistry.timer(getName(clazz,id));
    }
    
    private static String getName(Class<?> clazz, String id) {
        return MetricRegistry.name(clazz.getName(),id);
    }
}
