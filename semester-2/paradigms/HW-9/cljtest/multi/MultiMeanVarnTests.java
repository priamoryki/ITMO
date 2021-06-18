package cljtest.multi;

import java.util.Arrays;
import java.util.stream.DoubleStream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiMeanVarnTests extends MultiTests {
    public MultiMeanVarnTests(final boolean testMulti) {
        super(testMulti);

        any("mean", 1, args -> mean(Arrays.stream(args)));
        any("varn", 1, MultiMeanVarnTests::varn);
    }

    private static double varn(final double[] args) {
        final double mean = mean(Arrays.stream(args));
        return mean(Arrays.stream(args).map(a -> a - mean).map(a -> a * a));
    }

    private static double mean(final DoubleStream args) {
        return args.average().orElseThrow();
    }
}
