package expression;

/**
 * @author Pavel Lymar
 */
public interface Expression extends ToMiniString {
    public int evaluate(int x);

    public String toString();

    public boolean equals(Object x);
}