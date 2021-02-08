package expression;

/**
 * @author Pavel Lymar
 */
public interface AbstractComponent extends Expression, TripleExpression {
    public int evaluate(int x);

    public int evaluate(int x, int y, int z);

    public String toString();

    public boolean equals(Object x);
}