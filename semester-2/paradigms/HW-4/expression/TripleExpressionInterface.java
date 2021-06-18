package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpressionInterface<T> {
    T evaluate(T x, T y, T z);
}
