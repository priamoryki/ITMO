package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class FormatException extends ParsingException {
    public FormatException(String message, int index) {
        super(message, index);
    }
}