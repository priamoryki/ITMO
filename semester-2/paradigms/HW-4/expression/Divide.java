package expression;

/**
 * @author Pavel Lymar
 */
public class Divide<T> extends AbstractOperation<T> {
    public Divide(AbstractComponent<T> c1, AbstractComponent<T> c2, BinaryOperations<T> operationType) {
        super(c1, c2, operationType);
    }

    public T apply(T a, T b) {
        return operationType.divide(a, b);
    }
}