package expression;

import expression.exceptions.DivisionByZeroException;

public class LongOperations extends OperationsType<Long> {
    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long subtract(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long divide(Long a, Long b) {
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return a / b;
    }

    @Override
    public Long multiply(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long mod(Long a, Long b) {
        if (b == 0) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return a % b;
    }

    @Override
    public Long convert(String x) {
        return Long.parseLong(x);
    }

    @Override
    public Long unaryMinus(Long a) {
        return -a;
    }

    @Override
    public Long abs(Long a) {
        if (a >= 0) {
            return a;
        } else {
            return -a;
        }
    }
}