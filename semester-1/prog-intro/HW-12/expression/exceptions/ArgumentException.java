package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class ArgumentException extends ParsingException {
    public ArgumentException(String message, int index) {
        super(message, index);
    }
}