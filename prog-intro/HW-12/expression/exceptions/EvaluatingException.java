package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class EvaluatingException extends RuntimeException {
    public EvaluatingException(String message) {
        super(message);
    }
}