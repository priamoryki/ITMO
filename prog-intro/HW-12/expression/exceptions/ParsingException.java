package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class ParsingException extends Exception {
    public ParsingException(String message, int index) {
        super(message + " at index " + index);
    }
}