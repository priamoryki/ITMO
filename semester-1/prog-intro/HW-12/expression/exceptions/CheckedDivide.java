package expression.exceptions;

import expression.AbstractComponent;
import expression.AbstractOperation;

/**
 * @author Pavel Lymar
 */
public class CheckedDivide extends AbstractOperation {
    public CheckedDivide(AbstractComponent c1, AbstractComponent c2) {
        super(c1, c2, "/");
    }

    @Override
    public int apply(int a, int b) {
    	if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Division overflow: " + a + "/" + b);
        }
    	if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return a / b;
    }
}