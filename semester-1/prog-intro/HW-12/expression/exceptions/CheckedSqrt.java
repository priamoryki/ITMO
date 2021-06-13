package expression.exceptions;

import expression.AbstractComponent;
import expression.AbstractUnaryOperation;

/**
 * @author Pavel Lymar
 */
public class CheckedSqrt extends AbstractUnaryOperation {
	public CheckedSqrt(AbstractComponent c) {
		super(c, "sqrt");
	}
	
	@Override
	public int apply(int a) {
		if (a < 0) {
			throw new SqrtOfNegativeNumberException("Sqrt of negative number: " + a);
		}
		int left = 0, right = 46340; // sqrt(2 ** 31 - 1) = 46340.95
		while (left < right) {
			int m = (left + right - 1) / 2;
			if (m * m <= a) {
				left = m + 1;
			} else {
				right = m;
			}
		}
		return left - 1;
	}
}