package expression;

/**
 * @author Pavel Lymar
 */
public abstract class AbstractUnaryOperation<T> implements AbstractComponent<T> {
	private final AbstractComponent<T> component;
    protected final UnaryOperations<T> operationType;

    public AbstractUnaryOperation(AbstractComponent c, UnaryOperations<T> operationType) {
        this.component = c;
        this.operationType = operationType;
    }

    public T evaluate(T x) {
        return apply(this.component.evaluate(x));
    }

    public T evaluate(T x, T y, T z) {
        return apply(this.component.evaluate(x, y, z));
    }

    public abstract T apply(T a);
}