package jstest.functional;

import jstest.Language;

import java.util.Arrays;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class FunctionalOneMinMaxTest extends FunctionalOneTwoTest {
    public static class MinMaxTests extends OneTwoTests {{
        fixed("min5", 5, args -> Arrays.stream(args).min().orElseThrow());
        fixed("max3", 3, args -> Arrays.stream(args).max().orElseThrow());
        tests(
                f("min5", vx, vy, vz, one, two),
                f("max3", vx, vy, vz)
        );
    }}

    protected FunctionalOneMinMaxTest(final Language language, final boolean testParsing) {
        super(language, testParsing);
    }

    public static void main(final String... args) {
        test(args, FunctionalOneMinMaxTest.class, FunctionalOneMinMaxTest::new, new MinMaxTests());
    }
}
