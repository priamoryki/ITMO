package jstest.prefix;

import jstest.ArithmeticTests;
import jstest.Language;
import jstest.object.ObjectExpressionTest;

import java.util.Arrays;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrefixMeansTest extends PrefixParsingErrorTest {
    public static final Dialect MEANS_DIALECT = ObjectExpressionTest.ARITHMETIC_DIALECT.copy()
            .rename("arith-mean", "ArithMean")
            .rename("geom-mean", "GeomMean")
            .rename("harm-mean", "HarmMean");

    public static class MeansTests extends ArithmeticTests {
        public MeansTests() {
            any("arith-mean", 1, 5, mean((args, n) -> args.sum() / n));
            any("geom-mean", 1, 5, mean((args, n) -> Math.pow(Math.abs(product(args)), 1 / n)));
            any("harm-mean", 1, 5, mean((args, n) -> n / args.map(a -> 1 / a).sum()));

            tests("arith-mean");
            tests("geom-mean");
            tests("harm-mean");

            for (int i = 1; i < 10; i++) {
                final String[] means = Stream.generate(() -> randomItem("arith-mean", "geom-mean", "harm-mean"))
                        .limit(3).toArray(String[]::new);
                final TestExpression[] args = randomArgs(i);
                tests(
                        f("arith-mean", args),
                        f("geom-mean", args),
                        f("harm-mean", args),
                        f(means[0], f(means[1], vx, vy), f(means[2], vy, vz))
                );
            }
        }

        private void tests(final String mean) {
            tests(
                    f(mean, vx),
                    f(mean, vx, vy),
                    f(mean, vx, vy, vz),
                    f(mean, vx, vy, vz, c(3), c(5)),
                    f(mean, f("+", vx, c(2))),
                    f(mean, f("+", vx, vy)),
                    f(mean, f("negate", vz), f("/", vx, vy)),
                    f(mean, f("negate", vz), f(mean, vx, vy))
            );
        }
    }

    private static double product(final DoubleStream args) {
        return args.reduce(1, (a, b) -> a * b);
    }

    private static Func mean(final ToDoubleBiFunction<DoubleStream, Double> f) {
        return args -> f.applyAsDouble(Arrays.stream(args), (double) args.length);
    }

    protected PrefixMeansTest(final int mode, final Language language, final String toString) {
        super(mode, language, toString);
        insertions = "()+*/@ABC";
    }

    public static void main(final String... args) {
        test(PrefixMeansTest.class, PrefixMeansTest::new, new MeansTests(), args, MEANS_DIALECT, "prefix");
    }
}
