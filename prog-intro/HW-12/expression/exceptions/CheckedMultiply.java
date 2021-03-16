package expression.exceptions;

import expression.AbstractComponent;
import expression.AbstractOperation;

/**
 * @author Pavel Lymar
 */
public class CheckedMultiply extends AbstractOperation {
    public CheckedMultiply(AbstractComponent c1, AbstractComponent c2) {
        super(c1, c2, "*");
    }

    @Override
    public int apply(int a, int b) {
    	int maximum;
    	if ((a >= 0 && b >= 0) || (a < 0 && b < 0)) {
    		maximum = Integer.MAX_VALUE;
    	} else {
    		maximum = Integer.MIN_VALUE;
    	}

        if ((a == -1 && b == Integer.MIN_VALUE)
                || (a != 0 && a != -1 && ((b > 0 && b > maximum / a) || (b < 0 && b < maximum / a)))) {
            throw new OverflowException("Multiplication overflow: " + a + "*" + b);
        }
        return a * b;
    }
}