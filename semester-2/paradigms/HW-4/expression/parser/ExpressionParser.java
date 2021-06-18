package expression.parser;

import expression.*;

/**
 * @author Pavel Lymar
 */
public class ExpressionParser<T> implements ParserInterface<T> {
    private ExpressionSource source;
    private char currentChar;
    private OperationsType<T> operationType;

    public ExpressionParser(OperationsType<T> operationType) {
    	this.operationType = operationType;
    }

    private void nextChar() {
        if (source.hasNext()) {
            currentChar = source.next();
        } else {
            currentChar = '\0';
        }
    }

    private boolean isEquals(char x) {
        skipWhiteSpace();
        if (currentChar == x) {
            nextChar();
            return true;
        }
        return false;
    }

    private void skipWhiteSpace() {
        while (Character.isWhitespace(currentChar)) {
            nextChar();
        }
    }

    private Const<T> parseConst() {
        StringBuilder number = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            number.append(currentChar);
            nextChar();
        }
        skipWhiteSpace();
        return new Const(operationType.convert(number.toString()));
    }

    private String parseVariableName() {
        skipWhiteSpace();
        StringBuilder str = new StringBuilder();
        while (Character.isLetter(currentChar)) {
            str.append(currentChar);
            nextChar();
        }
        return str.toString();
    }

    @Override
    public TripleExpressionInterface<T> parse(String expression) {
        source = new StringSource(expression);
        nextChar();
        skipWhiteSpace();
        AbstractComponent<T> result = addSub();
        skipWhiteSpace();
        return result;
    }
    
    private AbstractComponent<T> addSub() {
    	AbstractComponent<T> result = mulDiv();
        skipWhiteSpace();
        while (true) {
            if (isEquals('+')) {
                result = new Add(result, mulDiv(), operationType);
            } else if (isEquals('-')) {
                result = new Subtract(result, mulDiv(), operationType);
            } else {
                return result;
            }
        }
    }

    private AbstractComponent<T> mulDiv() {
    	AbstractComponent<T> result = number();
        skipWhiteSpace();
        while (true) {
            if (isEquals('*')) {
                result = new Multiply(result, number(), operationType);
            } else if (isEquals('/')) {
                result = new Divide(result, number(), operationType);
            } else if (isEquals('m')) {
                if (isEquals('o')) {
                    if (isEquals('d')) {
                        result = new Mod(result, number(), operationType);
                    }
                }
            } else {
                return result;
            }
        }
    }

    private AbstractComponent<T> number() {
    	AbstractComponent<T> result;
        skipWhiteSpace();
        if (isEquals('(')) {
            result = addSub();
            nextChar();
        } else if (isEquals('-')) {
            if (Character.isDigit(currentChar)) {
                result = new UnaryMinus(parseConst(), operationType);
            } else {
            	result = new UnaryMinus(number(), operationType);
            }
        } else if (Character.isDigit(currentChar)) {
            result = parseConst();
        } else if (Character.isLetter(currentChar)) {
            String var = parseVariableName();
            if (var.equals("abs")) {
                result = new Abs(number(), operationType);
            } else if (var.equals("square")) {
                result = new Square(number(), operationType);
            } else  {
                result = new Variable(var);
            }
        } else {
            return null;
        }
        skipWhiteSpace();
        return result;
    }
}