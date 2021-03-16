package expression.parser;

/**
 * @author Pavel Lymar
 */
public interface ExpressionSource {
    char next();
    boolean hasNext();
    int getCurrentPosition();
}