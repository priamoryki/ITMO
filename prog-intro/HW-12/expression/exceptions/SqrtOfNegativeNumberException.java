package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class SqrtOfNegativeNumberException extends EvaluatingException {
    public SqrtOfNegativeNumberException(String message) {
        super(message);
    }
}