package info.kgeorgiy.ja.lymar.hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Pavel Lymar
 */
public class Utils {
    public final static int DEFAULT_TIMEOUT = 250;
    public final static int DEFAULT_CAPACITY = 1024;

    public static void close(ExecutorService service) {
        service.shutdown();
        try {
            if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
                service.shutdownNow();
            }
        } catch (InterruptedException ignored) {
        }
    }

    public static String createHelloRequest(String prefix, int thread, int requestId) {
        return prefix + thread + "_" + requestId;
    }
}
