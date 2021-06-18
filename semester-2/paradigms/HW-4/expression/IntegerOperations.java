package expression;

import expression.exceptions.*;

/**
 * @author Pavel Lymar
 */
public class IntegerOperations extends OperationsType<Integer> {
    private final boolean hasExceptions;
    public IntegerOperations (boolean hasExceptions) {
        this.hasExceptions = hasExceptions;
    }

    @Override
    public Integer add(Integer a, Integer b) {
        if (hasExceptions && ((b > 0 && a > Integer.MAX_VALUE - b) || (b < 0 && a < Integer.MIN_VALUE - b))) {
            throw new OverflowException("Addition overflow: " + a + "+" + b);
        }
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        if (hasExceptions && ((b > 0 && a < Integer.MIN_VALUE + b) || (b < 0 && a > Integer.MAX_VALUE + b))) {
            throw new OverflowException("Subtraction overflow: " + a + "-" + b);
        }
        return a - b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (hasExceptions && a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Division overflow: " + a + "/" + b);
        }
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return a / b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        int maximum;
        if (hasExceptions) {
            if ((a >= 0 && b >= 0) || (a < 0 && b < 0)) {
                maximum = Integer.MAX_VALUE;
            } else {
                maximum = Integer.MIN_VALUE;
            }

            if ((a == -1 && b == Integer.MIN_VALUE)
                    || (a != 0 && a != -1 && ((b > 0 && b > maximum / a) || (b < 0 && b < maximum / a)))) {
                throw new OverflowException("Multiplication overflow: " + a + "*" + b);
            }
        }
        return a * b;
    }

    @Override
    public Integer unaryMinus(Integer a) {
        if (hasExceptions && a == Integer.MIN_VALUE) {
            throw new OverflowException("Negate overflow: " + a);
        }
        return -a;
    }

    @Override
    public Integer convert(String x) {return Integer.parseInt(x);}

    @Override
    public Integer abs(Integer a) {
        if (a >= 0) {
            return a;
        } else {
            return unaryMinus(a);
        }
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return a % b;
    }
}