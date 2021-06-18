package prtest.parsing;

import jstest.expression.AbstractTests;
import jstest.expression.Operation;
import jstest.expression.Selector;

import java.util.function.BiConsumer;

import static jstest.expression.Operations.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ParserTest {
    private static final Selector SELECTOR = ParserTester.SELECTOR.copy()
            .add("Base", ARITH)
            .add("SinCos",                 ARITH, SIN,         COS)
            ;

    public static void main(final String... args) {
        SELECTOR.test(args);
    }
}
