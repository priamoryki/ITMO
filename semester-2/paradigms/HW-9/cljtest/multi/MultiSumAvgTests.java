package cljtest.multi;

import java.util.Arrays;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiSumAvgTests extends MultiTests {
    public MultiSumAvgTests(final boolean testMulti) {
        super(testMulti);

        any("sum", 0, args -> Arrays.stream(args).sum());
        any("avg", 1, args -> Arrays.stream(args).average().orElseThrow());
    }
}
