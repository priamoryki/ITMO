package expression.generic;

import expression.*;
import expression.exceptions.EvaluatingException;
import expression.parser.ExpressionParser;

/**
 * @author Pavel Lymar
 */
public class GenericTabulator implements Tabulator {
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        if (mode.equals("i")) {
            return solve(expression, x1, x2, y1, y2, z1, z2, new IntegerOperations(true));
        } else if (mode.equals("d")) {
            return solve(expression, x1, x2, y1, y2, z1, z2, new DoubleOperations());
        } else if (mode.equals("bi")) {
            return solve(expression, x1, x2, y1, y2, z1, z2, new BigIntegerOperations());
        } else if (mode.equals("u")) {
            return solve(expression, x1, x2, y1, y2, z1, z2, new IntegerOperations(false));
        } else if (mode.equals("l")) {
            return solve(expression, x1, x2, y1, y2, z1, z2, new LongOperations());
        } else {
            return solve(expression, x1, x2, y1, y2, z1, z2, new ShortOperations());
        }
    }

    private <T> Object[][][] solve(String expression, int x1, int x2, int y1, int y2, int z1, int z2, OperationsType<T> operationType) {
        ExpressionParser<T> parser = new ExpressionParser<>(operationType);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpressionInterface<T> tripleExpression = parser.parse(expression);
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    T i = operationType.convert(Integer.toString(x));
                    T j = operationType.convert(Integer.toString(y));
                    T k = operationType.convert(Integer.toString(z));
                    try {
                        result[x - x1][y - y1][z - z1] = tripleExpression.evaluate(i, j, k);
                    } catch (EvaluatingException e) {
                        result[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}