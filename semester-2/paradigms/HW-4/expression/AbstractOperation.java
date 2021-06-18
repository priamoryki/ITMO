package expression;

/**
 * @author Pavel Lymar
 */
public abstract class AbstractOperation<T> implements AbstractComponent<T> {
    private final AbstractComponent<T> left;
    private final AbstractComponent<T> right;
    protected final BinaryOperations<T> operationType;

    public AbstractOperation(AbstractComponent<T> c1, AbstractComponent<T> c2, BinaryOperations<T> operationType) {
        this.left = c1;
        this.right = c2;
        this.operationType = operationType;
    }

    public T evaluate(T x) {
        return apply(this.left.evaluate(x), this.right.evaluate(x));
    }

    public T evaluate(T x, T y, T z) {
        return apply(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
    }

    public abstract T apply(T a, T b);
}