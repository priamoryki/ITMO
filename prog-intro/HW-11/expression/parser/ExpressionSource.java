package expression.parser;

/**
 * @author Pavel Lymar
 */
public interface ExpressionSource {
    char next();
    char peekNext();
    boolean hasNext();
    int getCurrentPosition();
}