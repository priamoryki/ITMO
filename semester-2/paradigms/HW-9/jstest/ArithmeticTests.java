package jstest;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ArithmeticTests extends VariablesTests {
    private final Supplier<TestExpression> randomArg = () -> randomItem(vx, vy, vz, c(randomInt(10)), c(randomInt(10)));

    public ArithmeticTests() {
        addTests(vx, vy, vz);
    }

    public ArithmeticTests(final TestExpression vx, final TestExpression vy, final TestExpression vz) {
        addTests(vx, vy, vz);
    }

    protected TestExpression[] randomArgs(final int i) {
        return Stream.generate(randomArg).limit(i).toArray(TestExpression[]::new);
    }

    private void addTests(final TestExpression vx, final TestExpression vy, final TestExpression vz) {
        //noinspection Convert2MethodRef
        binary("+", (a, b) -> a + b);
        binary("-", (a, b) -> a - b);
        binary("*", (a, b) -> a * b);
        binary("/", (a, b) -> a / b);
        unary("negate", a -> -a);

        tests(
                c(10),
                vx,
                vy,
                vz,
                f("+", vx, c(2)),
                f("-", c(3), vy),
                f("*", c(4), vz),
                f("/", c(5), vz),
                f("/", f("negate", vx), c(2)),
                f("/", vx, f("*", vy, vz)),
                f("+", f("+", f("*", vx, vx), f("*", vy, vy)), f("*", vz, vz)),
                f("-", f("+", f("*", vx, vx), f("*", c(5), f("*", vz, f("*", vz, vz)))), f("*", vy, c(8)))
        );
    }
}
