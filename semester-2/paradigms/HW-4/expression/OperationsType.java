package expression;

/**
 * @author Pavel Lymar
 */
public abstract class OperationsType<T> implements BinaryOperations<T>, UnaryOperations<T> {
    public abstract T convert(String x);

    public T square(T a) {
        return multiply(a, a);
    }
}