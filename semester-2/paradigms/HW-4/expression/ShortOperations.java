package expression;

import expression.exceptions.DivisionByZeroException;

public class ShortOperations extends OperationsType<Short> {
    @Override
    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short divide(Short a, Short b) {
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return (short) (a / b);
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short mod(Short a, Short b) {
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return (short) (a % b);
    }

    @Override
    public Short convert(String x) {
        return (short) Integer.parseInt(x);
    }

    @Override
    public Short unaryMinus(Short a) {
        return (short) -a;
    }

    @Override
    public Short abs(Short a) {
        if (a > 0) {
            return a;
        } else {
            return (short) -a;
        }
    }
}