package jstest.functional;

import jstest.Language;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class FunctionalOneFPTest extends FunctionalOneTwoTest {
    public static final Dialect FP_FUNCTIONS = ARITHMETIC_FUNCTIONS.copy()
            .rename("*+", "madd")
            .rename("_", "floor")
            .rename("^", "ceil");

    public static class FPTests extends OneTwoTests {{
        final Func madd = args -> args[0] * args[1] + args[2];
        fixed("*+", 3, madd);
        fixed("madd", 3, madd);
        unary("_", Math::floor);
        unary("floor", Math::floor);
        unary("^", Math::ceil);
        unary("ceil", Math::ceil);

        tests(
                f("*+", f("-", vx, vy), vz, one),
                f("madd", f("-", vx, vy), vz, two),
                f("_", f("/", vx, vy)),
                f("floor", f("/", vx, vy)),
                f("^", f("-", vx, f("/", vy, c(3)))),
                f("ceil", f("-", vx, f("/", vy, c(3))))
        );
    }}

    protected FunctionalOneFPTest(final Language language, final boolean testParsing) {
        super(language, testParsing);
    }

    public static void main(final String... args) {
        test(args, FunctionalOneFPTest.class, FunctionalOneFPTest::new, new FPTests(), FP_FUNCTIONS);
    }
}
