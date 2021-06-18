package expression;

/**
 * @author Pavel Lymar
 */
public class Subtract<T> extends AbstractOperation<T> {
    public Subtract(AbstractComponent<T> c1, AbstractComponent<T> c2, BinaryOperations<T> operationType) {
        super(c1, c2, operationType);
    }

    @Override
    public T apply(T a, T b) {
        return operationType.subtract(a, b);
    }
}