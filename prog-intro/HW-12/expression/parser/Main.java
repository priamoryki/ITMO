package expression.parser;

import expression.*;

/**
 * @author Pavel Lymar
 */
public class Main {
	public static void main(String[] args) {
		final ExpressionParser parser = new ExpressionParser();
		TripleExpression a = parser.parse("(-341327948 ^ (1169896615 | ((0 - -1596834000) * (0 - y))))");
		System.out.print(a);
	}
}