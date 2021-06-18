package jstest;

import jstest.BaseJavascriptTest.Expr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Language {
    private final BaseJavascriptTest.Dialect parsed;
    private final BaseJavascriptTest.Dialect unparsed;

    protected final AbstractTests abstractTests;

    public final List<Expr> tests = new ArrayList<>();

    public Language(final BaseJavascriptTest.Dialect parsed, final BaseJavascriptTest.Dialect unparsed, final AbstractTests tests) {
        this.parsed = parsed;
        this.unparsed = unparsed;

        abstractTests = tests;
        this.tests.addAll(tests.renderTests(parsed, unparsed));
    }

    public Expr randomTest(final int size) {
        return abstractTests.randomTest(size, parsed, unparsed);
    }
}
