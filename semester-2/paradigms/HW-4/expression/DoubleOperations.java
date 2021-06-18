package expression;

import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

/**
 * @author Pavel Lymar
 */
public class DoubleOperations extends OperationsType<Double>{
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double unaryMinus(Double a) {
        return -a;
    }

    @Override
    public Double convert(String x) {
        return Double.parseDouble(x);
    }

    @Override
    public Double abs(Double a) {
        if (a >= 0) {
            return a;
        } else {
            return -a;
        }
    }

    @Override
    public Double mod(Double a, Double b) {
        return a % b;
    }
}