package cljtest.object;

import cljtest.multi.MultiSinhCoshTests;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureObjectSinhCoshTest {
    public static void main(final String... args) {
        ClojureObjectExpressionTest.test(
                args, MultiSinhCoshTests::new, ClojureObjectSinhCoshTest.class,
                ClojureObjectExpressionTest.PARSED.copy()
                        .rename("sinh", "Sinh")
                        .rename("cosh", "Cosh")
        );
    }
}
