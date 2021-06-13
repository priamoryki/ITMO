package expression.exceptions;

import expression.AbstractComponent;
import expression.AbstractUnaryOperation;

/**
 * @author Pavel Lymar
 */
public class CheckedAbs extends AbstractUnaryOperation {
	public CheckedAbs(AbstractComponent c) {
		super(c, "abs");
	}
	
	@Override
	public int apply(int a) {
		if (a == Integer.MIN_VALUE) {
			throw new OverflowException("Abs overflow: abs " + a);
		}
		if (a >= 0) {
			return a;
		} else {
			return -a;
		}
	}
}