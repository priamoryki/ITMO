package expression.exceptions;

/**
 * @author Pavel Lymar
 */
public class VariableNameException extends ParsingException {
    public VariableNameException(String s, int index) {
        super(s, index);
    }
}