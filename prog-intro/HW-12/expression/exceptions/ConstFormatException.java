package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class ConstFormatException extends ParsingException {
    public ConstFormatException(String message, int index) {
        super(message, index);
    }
}