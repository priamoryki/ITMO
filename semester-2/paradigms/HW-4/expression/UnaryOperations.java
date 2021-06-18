package expression;

/**
 * @author Pavel Lymar
 */
public interface UnaryOperations<T> {
    T unaryMinus(T a);

    T abs(T a);

    T square(T a);
}