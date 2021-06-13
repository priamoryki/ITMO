package expression.exceptions;

import java.util.Set;
import expression.AbstractComponent;
import expression.Const;
import expression.TripleExpression;
import expression.Variable;
import expression.parser.ExpressionSource;
import expression.parser.StringSource;

/**
 * @author Pavel Lymar
 */
public class ExpressionParser implements Parser {
	private ExpressionSource source;
    private char currentChar;
    private Set<String> allowedVariables = Set.of("x", "y", "z");
    
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

    private Const parseConst(String sourcePrefix) throws ParsingException {
        StringBuilder number = new StringBuilder(sourcePrefix);
        while (Character.isDigit(currentChar)) {
            number.append(currentChar);
            nextChar();
        }
        skipWhiteSpace();
        try {
            return new Const(Integer.parseInt(number.toString()));
        } catch (NumberFormatException e) {
            throw new ConstFormatException("Illegal const format", source.getCurrentPosition());
        }
    }

    private String parseVariableName() throws ParsingException {
        skipWhiteSpace();
        StringBuilder str = new StringBuilder();
        while (Character.isLetter(currentChar)) {
            str.append(currentChar);
            nextChar();
        }
        return str.toString();
    }

    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        source = new StringSource(expression);
        nextChar();
        skipWhiteSpace();
        AbstractComponent result = addSub();
        skipWhiteSpace();
        if (!isEquals('\0')) {
        	throw new FormatException("End of input expected", source.getCurrentPosition());
        }
        return result;
    }
    
    private AbstractComponent addSub() throws ParsingException {
    	AbstractComponent result = mulDiv();
        skipWhiteSpace();
        while (true) {
            if (isEquals('+')) {
            	result = new CheckedAdd(result, mulDiv());
            } else if (isEquals('-')) {
            	result = new CheckedSubtract(result, mulDiv());
            } else {
                return result;
            }
        }
    }

    private AbstractComponent mulDiv() throws ParsingException {
    	AbstractComponent result = number();
        skipWhiteSpace();
        while (true) {
            if (isEquals('*')) {
                result = new CheckedMultiply(result, number());
            } else if (isEquals('/')) {
                result = new CheckedDivide(result, number());
            } else {
                return result;
            }
        }
    }

    private AbstractComponent number() throws ParsingException {
    	AbstractComponent result;
        skipWhiteSpace();
        if (isEquals('(')) {
            result = addSub();
            if (!isEquals(')')) {
                throw new IllegalOperationException("')' expected", source.getCurrentPosition());
            }
        } else if (isEquals('-')) {
            if (Character.isDigit(currentChar)) {
                result = parseConst("-");
            } else {
            	result = new CheckedNegate(number());
            }
        } else if (Character.isDigit(currentChar)) {
            result = parseConst("");
        } else if (Character.isLetter(currentChar)) {
            String var = parseVariableName();
            if (var.equals("abs")) {
            	result = new CheckedAbs(number());
            } else if (var.equals("sqrt")) {
            	result = new CheckedSqrt(number());
            } else  {
            	if (!allowedVariables.contains(var)) {
            		throw new VariableNameException("Illegal variable name", source.getCurrentPosition());
            	}
            	result = new Variable(var);
            }
        } else {
        	throw new ArgumentException("No argument found", source.getCurrentPosition());
        }
        skipWhiteSpace();
        return result;
    }
}