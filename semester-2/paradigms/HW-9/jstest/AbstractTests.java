package jstest;

import base.Randomized;
import jstest.BaseJavascriptTest.Dialect;
import jstest.BaseJavascriptTest.Expr;
import jstest.BaseJavascriptTest.Func;

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class AbstractTests implements Randomized {
    private static final int MAX_C = 1_000_000;

    protected final Random random = Randomized.initRandom(8045702385702345704L, getClass());

    public final List<TestExpression> tests = new ArrayList<>();
    private final Map<String, Operator> operators = new HashMap<>();
    private final Map<String, Func> nullary = new HashMap<>();
    private final List<TestExpression> variables = new ArrayList<>();

    private final Map<String, Integer> variableNames = new HashMap<>();
    private final List<String> operatorNames = new ArrayList<>();
    private final List<String> nullaryNames = new ArrayList<>();

    public List<Expr> renderTests(final Dialect parsed, final Dialect unparsed) {
        return tests.stream().map(test -> test.render(parsed, unparsed)).collect(Collectors.toList());
    }

    public Expr randomTest(final int size, final Dialect parsed, final Dialect unparsed) {
        return generate(size / 5 + 2).render(parsed, unparsed);
    }

    private TestExpression generate(final int depth) {
        if (depth > 0) {
            return generateOp(depth);
        } else if (randomBoolean()) {
            return randomItem(variables);
        } else if (nullary.isEmpty() || randomBoolean()){
            return c(randomInt(-MAX_C, MAX_C));
        } else {
            return n(nullaryNames.get(randomInt(nullaryNames.size())));
        }
    }

    protected TestExpression generateOp(final int depth) {
        if (randomInt(6) == 0 || operatorNames.isEmpty()) {
            return generateP(depth);
        } else {
            final String name = operatorNames.get(randomInt(operatorNames.size()));
            final Operator operator = operators.get(name);
            final TestExpression[] args = Stream.generate(() -> generateP(depth))
                    .limit(randomInt(operator.minArity, operator.maxArity))
                    .toArray(TestExpression[]::new);
            return f(name, args);
        }
    }

    protected TestExpression generateP(final int depth) {
        return generate(randomInt(depth));
    }

    protected void tests(final TestExpression... tests) {
        this.tests.addAll(Arrays.asList(tests));
    }

    protected void fixed(final String name, final int arity, final Func f) {
        any(name, arity, arity, f);
    }

    protected void any(final String name, final int minArity, final int maxArity, final Func f) {
        operatorNames.add(name);
        operators.put(name, new Operator(minArity, maxArity, f));
    }

    public void unary(final String name, final DoubleUnaryOperator answer) {
        fixed(name, 1, args -> answer.applyAsDouble(args[0]));
    }

    public void binary(final String name, final DoubleBinaryOperator answer) {
        fixed(name, 2, args -> answer.applyAsDouble(args[0], args[1]));
    }

    public void nullary(final String name, final Func f) {
        nullaryNames.add(name);
        nullary.put(name, f);
    }

    protected TestExpression f(final String name, final TestExpression... args) {
        return (parsed, unparsed) -> {
            final List<Expr> rendered = Arrays.stream(args).map(a -> a.render(parsed, unparsed)).collect(Collectors.toList());
            return expr(
                    parsed.operation(name, rendered.stream().map(a -> a.parsed).collect(Collectors.toList())),
                    unparsed.operation(name, rendered.stream().map(a -> a.unparsed).collect(Collectors.toList())),
                    vars -> {
                        final Operator operator = operators.get(name);
                        assert operator != null : "Unknown operation " + name + " in " + getClass().getSimpleName();
                        return operator.applyAsDouble(rendered.stream()
                                .map(a -> a.answer)
                                .mapToDouble(a -> a.applyAsDouble(vars))
                                .toArray());
                    }
            );
        };
    }

    protected TestExpression n(final String name) {
        return (parsed, unparsed) -> expr(Dialect.nullary(name), Dialect.nullary(name), nullary.get(name));
    }

    protected static TestExpression c(final int value) {
        return (parsed, unparsed) -> expr(parsed.constant(value), unparsed.constant(value), vars -> value);
    }

    protected TestExpression variable(final String name, final int index) {
        final TestExpression variable = (parsed, unparsed) -> expr(parsed.variable(name), unparsed.variable(name), vars -> vars[index]);
        variables.add(variable);
        variableNames.put(name, index);
        return variable;
    }

    public static Expr expr(final String parsed, final String unparsed, final Func answer) {
        return new Expr(parsed, unparsed, answer);
    }

    public List<TestExpression> getVariables() {
        return variables;
    }

    public Map<String, Integer> getVariableNames() {
        return variableNames;
    }

    @Override
    public Random getRandom() {
        return random;
    }

    protected interface TestExpression {
        Expr render(Dialect parsed, Dialect unparsed);
    }

    private static class Operator implements Func {
        public final int minArity, maxArity;
        public Func f;

        public Operator(final int minArity, final int maxArity, final Func f) {
            assert 0 <= minArity && minArity <= maxArity;

            this.minArity = minArity;
            this.maxArity = maxArity;
            this.f = f;
        }

        @Override
        public double applyAsDouble(final double[] args) {
            return Arrays.stream(args).allMatch(Double::isFinite) ? f.applyAsDouble(args) : Double.NaN;
        }

    }
}
