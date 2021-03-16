package expression.exceptions;

import expression.AbstractComponent;
import expression.AbstractUnaryOperation;

/**
 * @author Pavel Lymar
 */
public class CheckedNegate extends AbstractUnaryOperation {
	public CheckedNegate(AbstractComponent c) {
		super(c, "-");
	}
	
	public int apply(int a) {
		if (a == Integer.MIN_VALUE) {
            throw new OverflowException("Negate overflow: " + a);
        }
		return -a;
	}
}