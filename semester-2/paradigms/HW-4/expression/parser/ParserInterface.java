package expression.parser;

import expression.TripleExpression;
import expression.TripleExpressionInterface;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ParserInterface<T> {
    TripleExpressionInterface<T> parse(String expression);
}