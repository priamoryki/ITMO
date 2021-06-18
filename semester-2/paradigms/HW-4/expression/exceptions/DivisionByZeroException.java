package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class DivisionByZeroException extends EvaluatingException {
    public DivisionByZeroException(String message) {
        super(message);
    }
}