package expression.parser;

/**
 * @author Pavel Lymar
 */
public class StringSource implements ExpressionSource {
    private final String source;
    private int id;

    public StringSource(final String source) {
        this.source = source;
        id = 0;
    }

    @Override
    public char next() {
        return source.charAt(id++);
    }

    @Override
    public char peekNext() {
        if (hasNext()) {
            return source.charAt(id);
        } else {
            return '\0';
        }
    }

    @Override
    public boolean hasNext() {
        return id < source.length();
    }

    @Override
    public int getCurrentPosition() {
        return id;
    }
}