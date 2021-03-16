package expression.exceptions;

import expression.AbstractComponent;
import expression.AbstractOperation;

/**
 * @author Pavel Lymar
 */
public class CheckedAdd extends AbstractOperation {
    public CheckedAdd(AbstractComponent c1, AbstractComponent c2) {
        super(c1, c2, "+");
    }

    @Override
    public int apply(int a, int b) {
    	if ((b > 0 && a > Integer.MAX_VALUE - b) || (b < 0 && a < Integer.MIN_VALUE - b)) {
            throw new OverflowException("Addition overflow: " + a + "+" + b);
        }
        return a + b;
    }
}