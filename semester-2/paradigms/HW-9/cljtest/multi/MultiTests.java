package cljtest.multi;

import jstest.ArithmeticTests;
import jstest.BaseJavascriptTest;
import jstest.VariablesTests;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiTests extends VariablesTests {
    private final boolean testMulti;
    private final int maxArity;

    public MultiTests(final boolean testMulti) {
        this.testMulti = testMulti;
        maxArity = testMulti ? 5 : 2;

        unary("negate", a -> -a);
        tests.addAll(new ArithmeticTests().tests);

        any("+", 0, arith(0, Double::sum));
        any("-", 1, arith(0, (a, b) -> a - b));
        any("*", 0, arith(1, (a, b) -> a * b));
        any("/", 1, arith(1, (a, b) -> a / b));
    }

    protected void any(final String name, final int minArity, final BaseJavascriptTest.Func f) {
        if (testMulti) {
            any(name, minArity, maxArity, f);
        } else {
            fixed(name, 2, f);
        }

        tests(
                f(name, vx, vy),
                f(name, f("negate", vz), f("+", vx, vy)),
                f(name, f("-", vz, vy), f("negate", vx))
        );

        if (name.hashCode() != "sumexp".hashCode()) {
            tests(
                    f(name, f("negate", vz), f(name, vx, vy)),
                    f(name, f(name, vx, vy), f("negate", vz))
            );
        }

        if (testMulti) {
            tests(
                    f(name, vx),
                    f(name, vx, vy, vz),
                    f(name, vx, vy, vz, c(3), c(5)),
                    f(name, f("+", vx, c(2))),
                    f(name, f("+", vx, vy))
            );
        }

        final Supplier<TestExpression> generator = () -> randomItem(vx, vy, vz, c(randomInt(10)), c(randomInt(10)));
        for (int i = 1; i < 10; i++) {
            tests(f(name, Stream.generate(generator).limit(testMulti ? i : 2).toArray(TestExpression[]::new)));
        }
    }

    private static BaseJavascriptTest.Func arith(final double zero, final DoubleBinaryOperator f) {
        return args -> args.length == 0 ? zero
                     : args.length == 1 ? f.applyAsDouble(zero, args[0])
                     : Arrays.stream(args).reduce(f).orElseThrow();
    }
}
