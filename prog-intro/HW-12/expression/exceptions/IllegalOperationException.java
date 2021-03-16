package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class IllegalOperationException extends ParsingException {
    public IllegalOperationException(String message, int index) {
        super(message, index);
    }
}