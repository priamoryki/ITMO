package expression;

/**
 * @author Pavel Lymar
 */
public interface ExpressionInterface<T> {
    T evaluate(T x);
}