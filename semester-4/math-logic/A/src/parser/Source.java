package src.parser;

/**
 * @author Pavel Lymar
 */
public class Source {
    private final String source;
    private int id;

    public Source(final String source) {
        this.source = source;
        id = 0;
    }

    public char next() {
        return source.charAt(id++);
    }

    public boolean hasNext() {
        return id < source.length();
    }
}
