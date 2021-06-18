package jstest.object;

import jstest.ArithmeticTests;
import jstest.Language;

import java.util.Arrays;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ObjectAvgMedTest extends ObjectExpressionTest {
    public static final Dialect AVG_MED_DIALECT = ObjectExpressionTest.ARITHMETIC_DIALECT.copy()
            .rename("med3", "Med3")
            .rename("avg5", "Avg5");

    public static class AvgMedTests extends ArithmeticTests {{
        fixed("avg5", 5, args -> Arrays.stream(args).summaryStatistics().getAverage());
        fixed("med3", 3, args -> Arrays.stream(args).sorted().skip(1).findFirst().orElseThrow());
        tests(
                f("med3", vx, vy, vz),
                f("avg5", vx, vy, vz, c(7), f("*", vy, vz)),
                f("med3", vx, f("-", vy, vz), c(7)),
                f("/",
                        f("/",
                                f("-", vz, vz),
                                f("+",
                                        vx,
                                        f("-", vz, c(1777340624))
                                )
                        ),
                        f("-", vy, vy))
        );
    }}

    protected ObjectAvgMedTest(final int mode, final Language language) {
        super(mode, language);
    }

    public static void main(final String... args) {
        test(ObjectAvgMedTest.class, ObjectAvgMedTest::new, new AvgMedTests(), args, AVG_MED_DIALECT);
    }
}
