package expression;

/**
 * @author Pavel Lymar
 */
public class UnaryMinus<T> extends AbstractUnaryOperation<T> {
	public UnaryMinus(AbstractComponent<T> c, UnaryOperations<T> operationType) {
		super(c, operationType);
	}

	public T apply(T a) {
		return operationType.unaryMinus(a);
	}
}