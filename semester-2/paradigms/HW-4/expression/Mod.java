package expression;

public class Mod<T> extends AbstractOperation<T> {
    public Mod(AbstractComponent<T> c1, AbstractComponent<T> c2, BinaryOperations<T> operationType) {
        super(c1, c2, operationType);
    }

    @Override
    public T apply(T a, T b) {
        return operationType.mod(a, b);
    }
}