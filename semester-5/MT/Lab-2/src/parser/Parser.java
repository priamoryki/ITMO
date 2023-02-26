package parser;

import java.text.ParseException;

/**
 * @author Pavel Lymar
 * S -> var VARIABLE: T;
 * T -> Array<R>
 * R -> Int | String
 * R -> Map<R, R>
 * R -> T
 * VARIABLE = [a-zA-Z_][a-zA-Z_0-9]*
 */
public class Parser {
    private final Tokenizer tokenizer;
    private Token token;

    public Parser(String text) {
        tokenizer = new Tokenizer(text);
    }

    public Node parse() throws ParseException {
        return S();
    }

    private Node S() throws ParseException {
        token = tokenizer.nextToken();
        if (token != Token.VAR) {
            throw parseException("can't find var keyword");
        }

        token = tokenizer.nextToken();
        String name = tokenizer.getBuilder().toString();
        if (token != Token.VARIABLE) {
            throw parseException("can't find variable name");
        }

        token = tokenizer.nextToken();
        if (token != Token.COLON) {
            throw parseException("can't find type definition");
        }

        token = tokenizer.nextToken();
        Node result = T();

        token = tokenizer.nextToken();
        if (token != Token.SEMICOLON) {
            throw parseException("can't find \";\"");
        }

        token = tokenizer.nextToken();
        if (token != Token.END) {
            throw parseException("expression is parsed, but next token exists");
        }

        return new Node("var " + name, new Node(":"), result, new Node(";"));
    }

    private Node T() throws ParseException {
        switch (token) {
            case ARR:
                token = tokenizer.nextToken();
                if (token != Token.L_ANGLE_BRACKET) {
                    throw parseException("can't find \"<\"");
                }

                token = tokenizer.nextToken();
                Node result = R();

                token = tokenizer.nextToken();
                if (token != Token.R_ANGLE_BRACKET) {
                    throw parseException("can't find \">\"");
                }

                return new Node("Array", new Node("<"), result, new Node(">"));
            default:
                throw parseException("can't find Array type");
        }
    }

    private Node R() throws ParseException {
        switch (token) {
            case INT:
                return new Node("Int");
            case STRING:
                return new Node("String");
            case MAP:
                Node left, right;
                token = tokenizer.nextToken();
                if (token != Token.L_ANGLE_BRACKET) {
                    throw parseException("can't find \"<\"");
                }

                token = tokenizer.nextToken();
                try {
                    left = R();
                } catch (ParseException e) {
                    throw parseException("can't find Map Key type");
                }

                token = tokenizer.nextToken();
                if (token != Token.COMMA) {
                    throw parseException("can't find \",\"");
                }

                token = tokenizer.nextToken();
                try {
                    right = R();
                } catch (ParseException e) {
                    throw parseException("can't find Map Value type");
                }

                token = tokenizer.nextToken();
                if (token != Token.R_ANGLE_BRACKET) {
                    throw parseException("can't find \">\"");
                }

                return new Node("Map", new Node("<"), left, new Node(","), right, new Node(">"));
            default:
                return T();
        }

    }

    private ParseException parseException(String message) {
        return new ParseException(message, tokenizer.getPos());
    }
}
