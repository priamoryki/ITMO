package expression;

/**
 * @author Pavel Lymar
 */
public interface BinaryOperations<T> {
    T add(T a, T b);

    T subtract(T a, T b);

    T divide(T a, T b);

    T multiply(T a, T b);

    T mod(T a, T b);
}