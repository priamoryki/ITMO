package parser;

/**
 * @author Pavel Lymar
 */
public class Tokenizer {
    private static final char EOF = '\0';
    private static final String VARIABLE_NAME_PATTERN = "[a-zA-Z_][a-zA-Z_0-9]*";
    private final StringWrapper text;
    private StringBuilder builder;

    public Tokenizer(StringWrapper text) {
        this.text = text;
    }

    public Tokenizer(String text) {
        this(new StringWrapper(text));
    }

    public int getPos() {
        return text.getId();
    }

    public StringBuilder getBuilder() {
        return builder;
    }

    public Token nextToken() {
        builder = new StringBuilder();

        while (text.hasNext() && text.isWhitespace()) {
            text.next();
        }

        char cur = text.next();
        builder.append(cur);
        return switch (cur) {
            case '<' -> Token.L_ANGLE_BRACKET;
            case '>' -> Token.R_ANGLE_BRACKET;
            case ':' -> Token.COLON;
            case ';' -> Token.SEMICOLON;
            case ',' -> Token.COMMA;
            case EOF -> Token.END;
            default -> parseKeyword();
        };
    }

    private Token parseKeyword() {
        while (text.hasNext() && !text.isWhitespace()) {
            char cur = text.next();
            if (cur == '<' || cur == '>' || cur == ':' || cur == ';' || cur == ',') {
                text.prev();
                break;
            }
            builder.append(cur);
        }
        String token = builder.toString();
        return switch (token) {
            case "var" -> Token.VAR;
            case "Array" -> Token.ARR;
            case "Int" -> Token.INT;
            case "String" -> Token.STRING;
            case "Map" -> Token.MAP;
            default -> token.matches(VARIABLE_NAME_PATTERN) ? Token.VARIABLE : Token.ERROR;
        };
    }
}
