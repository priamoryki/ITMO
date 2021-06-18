package expression;

import expression.exceptions.*;
import java.math.BigInteger;

/**
 * @author Pavel Lymar
 */
public class BigIntegerOperations extends OperationsType<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            throw new DivisionByZeroException("Division by zero: " + a + "/" + b);
        }
        return a.divide(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger unaryMinus(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger convert(String a) {
        return new BigInteger(a);
    }

    @Override
    public BigInteger abs(BigInteger a) {
        if (a.compareTo(BigInteger.ZERO) >= 0) {
            return a;
        } else {
            return a.negate();
        }
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.ZERO) <= 0) {
            throw new EvaluatingException("Mod from negative number: " + a + "%" + b);
        }
        return a.mod(b);
    }
}