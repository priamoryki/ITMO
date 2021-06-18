package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class OverflowException extends EvaluatingException {
    public OverflowException(String message) {
        super(message);
    }
}