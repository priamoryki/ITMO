package parser;

/**
 * @author Pavel Lymar
 */
public class StringWrapper {
    private static final char EOF = '\0';
    private final String text;
    private int id;

    public StringWrapper(String text) {
        this.text = text;
    }

    public boolean hasNext() {
        return id < text.length();
    }

    public int getId() {
        return id;
    }

    public char next() {
        return getOrEOF(id++);
    }

    public char cur() {
        return getOrEOF(id);
    }

    public char prev() {
        return getOrEOF(id--);
    }

    public boolean isWhitespace() {
        return Character.isWhitespace(cur());
    }

    private char getOrEOF(int id) {
        return 0 <= id && id < text.length() ? text.charAt(id) : EOF;
    }
}
