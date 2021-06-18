package cljtest.parsing;

import cljtest.ClojureScript;
import cljtest.object.ClojureObjectExpressionTest;
import jstest.ArithmeticTests;
import jstest.Engine;
import jstest.Language;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntPredicate;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureObjectParsingTest extends ClojureObjectExpressionTest {
    public static final ClojureScript.F<String> TO_INFIX = ClojureScript.function("toStringInfix", String.class);
    public static final ClojureScript.F<String> TO_SUFFIX = ClojureScript.function("toStringSuffix", String.class);

    public static final Dialect INFIX = dialect(
            "%s",
            "%s.0",
            (op, args) -> {
                switch (args.size()) {
                    case 1: return op + "(" + args.get(0) + ")";
                    case 2: return "(" + args.get(0) + " " + op + " " + args.get(1) + ")";
                    default: throw new AssertionError("Unsupported op " + op + "/" + args.size());
                }
            }
    );
    public static final Dialect SUFFIX = dialect(
            "%s",
            "%s.0",
            (op, args) -> "("+ String.join(" ", args) + " " + op + ")"
    );

    private final boolean hard;
    private final Set<String> vars = new HashSet<>(Set.of("x", "y", "z"));
    private final Map<String, Integer> priorities;

    private ClojureObjectParsingTest(final Dialect parsed, final PriorityTests tests, final boolean hard) {
        super(new Language(parsed, hard ? INFIX : SUFFIX, tests), false);
        vars.addAll(tests.getVariableNames().keySet());
        priorities = tests.priorities;
        this.hard = hard;
    }

    protected void testToString(final String expression, final String expected) {
        engine.parse(expression);
        final Engine.Result<String> result = engine.toString(hard ? TO_INFIX : TO_SUFFIX);
        assertEquals(result.context, expected, result.value);
    }

    @Override
    protected String parse(String expression) {
        if (hard && random.nextBoolean()) {
            final Parsed parsed = new Parser(expression, vars, priorities).parse();
            final String converted = parsed.convert(new StringBuilder(), 0).toString();
//            System.out.println("from: " + expression);
//            System.out.println("      " + converted);
            expression = addSpaces(converted, random);
        }
        final String f = hard ? "parseObjectInfix" : "parseObjectSuffix";
        return "(" + f + " \"" + expression + "\")";
    }

    public static void main(final String... args) {
        test(args, new PriorityTests(), ClojureObjectParsingTest.class, PARSED);
    }

    protected static void test(final String[] args, final PriorityTests tests, final Class<?> test, final Dialect parsed) {
        new ClojureObjectParsingTest(parsed, tests, mode(args, test)).run();
    }

    interface Parsed {
        StringBuilder convert(StringBuilder sb, int priority);
    }

    static class Parser {
        private final String expression;
        private final Set<String> vars;
        private final Map<String, Integer> priorities;
        int pos = 0;

        public Parser(final String expression, final Set<String> vars, final Map<String, Integer> priorities) {
            this.expression = expression + "$";
            this.vars = vars;
            this.priorities = priorities;
        }

        public Parsed parse() {
            skipSpaces();
            if (test('(')) {
                final Parsed left = parse();
                skipSpaces();
                final String op = parseIdentifier();
                final Parsed right = parse();
                skipSpaces();
                expect(')');
                return (sb, priority) -> {
                    final int p = priorities.get(op);
                    final int local = Math.abs(p);
                    final int l = local + (p > 0 ? 0 : 1);
                    final int r = local + (p > 0 ? 1 : 0);
                    if (local < priority) {
                        return right.convert(left.convert(sb.append("("), l).append(op), r).append(")");
                    } else {
                        return right.convert(left.convert(sb, l).append(op), r);
                    }
                };
            } else if (Character.isDigit(getChar()) || getChar() == '-') {
                final char first = getChar();
                pos++;
                final String value = first + get(ch -> Character.isDigit(ch) || ch == '.');
                return (sb, priority) -> sb.append(value);
            } else {
                final String identifier = parseIdentifier();
                if (vars.contains(identifier)) {
                    return (sb, priority) -> sb.append(identifier);
                } else {
                    skipSpaces();
                    expect('(');
                    final Parsed arg = parse();
                    skipSpaces();
                    expect(')');
                    return (sb, priority) -> arg.convert(sb.append(identifier).append(" "), Integer.MAX_VALUE);
                }
            }
        }

        private static final String SYMBOLS = "*/-+&|^<>=";

        private String parseIdentifier() {
            final char first = getChar();
            if (Character.isLetter(first)) {
                return get(Character::isLetterOrDigit);
            } else {
                return get(ch -> SYMBOLS.indexOf(ch) >= 0);
            }
        }

        private void expect(final char ch) {
            if (!test(ch)) {
                throw new AssertionError(String.format("%d: expected '%c', found '%c'", pos + 1, ch, getChar()));
            }
        }

        private char getChar() {
            return expression.charAt(pos);
        }

        private boolean test(final char ch) {
            if (getChar() == ch) {
                pos++;
                return true;
            }
            return false;
        }

        private void skipSpaces() {
            get(Character::isWhitespace);
        }

        private String get(final IntPredicate p) {
            final int start = pos;
            while (p.test(getChar())) {
                pos++;
            }
            return expression.substring(start, pos);
        }
    }

    protected static class PriorityTests extends ArithmeticTests {
        private final Map<String, Integer> priorities = new HashMap<>(Map.of(
                "+", 100, "-", 100,
                "*", 200, "/", 200
        ));

        protected void binary(final String name, final int priority, final DoubleBinaryOperator answer) {
            binary(name, answer);
            priorities.put(name, priority);

            tests(
                    f(name, vx, vy),
                    f(name, vx, f("negate", vy)),
                    f(name, f("negate", vx), vy),
                    f(name, f("negate", vx), f("negate", vy)),
                    f(name, vx, f("-", c(1), vy)),
                    f(name, f("-", vx, c(2)), vy)
            );
        }
    }
}
