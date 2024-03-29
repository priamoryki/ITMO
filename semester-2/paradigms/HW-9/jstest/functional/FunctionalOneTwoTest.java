package jstest.functional;

import jstest.ArithmeticTests;
import jstest.Language;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class FunctionalOneTwoTest extends FunctionalExpressionTest {
    public static class OneTwoTests extends ArithmeticTests {
        protected final TestExpression one = constant(1, "one");
        protected final TestExpression two = constant(2, "two");

        {
            tests(
                    f("+", one, vx),
                    f("-", vy, two),
                    f("*", vx, one),
                    f("/", two, vy)
            );
        }

        private TestExpression constant(final double value, final String name) {
            final Func expr = vars -> value;
            nullary(name, expr);
            return (parsed, unparsed) -> expr(name, name, expr);
       }
    }

    protected FunctionalOneTwoTest(final Language language, final boolean testParsing) {
        super(language, testParsing);
    }

    public static void main(final String... args) {
        test(args, FunctionalOneTwoTest.class, FunctionalOneTwoTest::new, new OneTwoTests());
    }
}
