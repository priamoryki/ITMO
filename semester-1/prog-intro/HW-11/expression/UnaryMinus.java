package expression;

/**
 * @author Pavel Lymar
 */
public class UnaryMinus extends AbstractUnaryOperation {
	public UnaryMinus(AbstractComponent c) {
		super(c, "-");
	}

	public int apply(int a) {
		return -a;
	}
}