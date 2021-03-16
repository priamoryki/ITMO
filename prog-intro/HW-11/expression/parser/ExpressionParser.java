package expression.parser;

import expression.*;

/**
 * @author Pavel Lymar
 */
public class ExpressionParser implements Parser {
    private ExpressionSource source;
    private char currentChar;

    public ExpressionParser() {
    	
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

    private Const parseConst(String sourcePrefix) {
        StringBuilder number = new StringBuilder(sourcePrefix);
        while (Character.isDigit(currentChar)) {
            number.append(currentChar);
            nextChar();
        }
        skipWhiteSpace();
        return new Const(Integer.parseInt(number.toString()));
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
    public TripleExpression parse(String expression) {
        source = new StringSource(expression);
        nextChar();
        skipWhiteSpace();
        AbstractComponent result = or();
        skipWhiteSpace();
        return result;
    }
    
    private AbstractComponent or() {
    	AbstractComponent result = xor();
    	skipWhiteSpace();
        while (true) {
        	if (isEquals('|')) {
                result = new Or(result, xor());
            } else {
                return result;
            }
        }
    }
    
    private AbstractComponent xor() {
    	AbstractComponent result = and();
    	skipWhiteSpace();
        while (true) {
        	if (isEquals('^')) {
                result = new Xor(result, and());
            } else {
                return result;
            }
        }
    }
    
    private AbstractComponent and() {
    	AbstractComponent result = addSub();
    	skipWhiteSpace();
        while (true) {
        	if (isEquals('&')) {
            	result = new And(result, addSub());
            } else {
                return result;
            }
        }
    }
    
    private AbstractComponent addSub() {
    	AbstractComponent result = mulDiv();
        skipWhiteSpace();
        while (true) {
            if (isEquals('+')) {
                result = new Add(result, mulDiv());
            } else if (isEquals('-')) {
                result = new Subtract(result, mulDiv());
            } else {
                return result;
            }
        }
    }

    private AbstractComponent mulDiv() {
    	AbstractComponent result = number();
        skipWhiteSpace();
        while (true) {
            if (isEquals('*')) {
                result = new Multiply(result, number());
            } else if (isEquals('/')) {
                result = new Divide(result, number());
            } else {
                return result;
            }
        }
    }

    private AbstractComponent number() {
    	AbstractComponent result;
        skipWhiteSpace();
        if (isEquals('(')) {
            result = or();
            nextChar();
        } else if (isEquals('-')) {
            if (Character.isDigit(currentChar)) {
                result = parseConst("-");
            } else {
            	result = new UnaryMinus(number());
            }
        } else if (Character.isDigit(currentChar)) {
            result = parseConst("");
        } else if (Character.isLetter(currentChar)) {
            String var = parseVariableName();
            result = new Variable(var);
        } else {
            return null;
        }
        skipWhiteSpace();
        return result;
    }
}