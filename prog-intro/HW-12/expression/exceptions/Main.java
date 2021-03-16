package expression.exceptions;

import expression.*;

/**
 * @author Pavel Lymar
 */
public class Main {
	public static void main(String[] args) {
		final ExpressionParser parser = new ExpressionParser();
		try {
			TripleExpression a = parser.parse("abs -4");
			System.out.print(a.evaluate(0, 0, 0));
		} catch (ParsingException e) {
			System.out.print(e.getMessage());
		}
	}
}