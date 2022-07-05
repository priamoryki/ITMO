package src.parser;

import src.operations.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Lymar
 */
public class Parser {
    private final Source source;
    private final Set<String> vars;
    private char currentChar;

    public Parser(Source source) {
        this.source = source;
        this.vars = new HashSet<>();
    }

    private void nextChar() {
        currentChar = source.hasNext() ? source.next() : '\0';
    }

    private void skipWhiteSpaces() {
        while (Character.isWhitespace(currentChar)) {
            nextChar();
        }
    }

    private boolean isEquals(char x) {
        skipWhiteSpaces();
        if (currentChar == x) {
            nextChar();
            return true;
        }
        return false;
    }

    private boolean isVariableChar() {
        return Character.isLetterOrDigit(currentChar) || currentChar == '\'' || currentChar == '’';
    }

    public Component parse() {
        nextChar();
        skipWhiteSpaces();
        Component result = implication();
        skipWhiteSpaces();
        return result;
    }

    private Component implication() {
        Component result = or();
        skipWhiteSpaces();
        if (isEquals('-') && isEquals('>')) {
            result = new Implication(result, implication());
        }
        return result;
    }

    private Component or() {
        Component result = and();
        skipWhiteSpaces();
        if (isEquals('|')) {
            result = new Or(result, or());
        }
        return result;
    }

    private Component and() {
        Component result = not();
        skipWhiteSpaces();
        if (isEquals('&')) {
            result = new And(result, and());
        }
        return result;
    }

    private Component not() {
        Component result = null;
        skipWhiteSpaces();
        if (isEquals('!')) {
            result = new Not(not());
        } else if (isEquals('(')) {
            result = implication();
            nextChar();
        } else if (isVariableChar()) {
            result = variable();
        }
        return result;
    }

    private Component variable() {
        skipWhiteSpaces();
        StringBuilder builder = new StringBuilder();
        while (isVariableChar()) {
            builder.append(currentChar);
            nextChar();
        }
        vars.add(builder.toString());
        return new Variable(builder.toString());
    }

    public Set<String> getVars() {
        return vars;
    }
}
