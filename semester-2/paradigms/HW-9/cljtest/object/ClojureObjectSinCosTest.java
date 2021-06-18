package cljtest.object;

import cljtest.multi.MultiSinCosTests;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureObjectSinCosTest {
    public static void main(final String... args) {
        ClojureObjectExpressionTest.test(
                args, MultiSinCosTests::new, ClojureObjectSinCosTest.class,
                ClojureObjectExpressionTest.PARSED.copy()
                        .rename("sin", "Sin")
                        .rename("cos", "Cos")
        );
    }
}
