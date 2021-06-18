package expression;

/**
 * @author Pavel Lymar
 */
public class Abs<T> extends AbstractUnaryOperation<T> {
	public Abs(AbstractComponent<T> c, UnaryOperations<T> operationType) {
		super(c, operationType);
	}
	
	@Override
	public T apply(T a) {
		return operationType.abs(a);
	}
}