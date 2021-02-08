package expression;

/**
 * @author Pavel Lymar
 */
public abstract class AbstractOperation implements AbstractComponent {
    private final AbstractComponent left;
    private final AbstractComponent right;
    private final String sign;

    public AbstractOperation(AbstractComponent c1, AbstractComponent c2, String sign) {
        this.left = c1;
        this.right = c2;
        this.sign = sign;
    }

    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    public int evaluate(int x, int y, int z) {
        return apply(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
    }

    public abstract int apply(int a, int b);

    public String toString() {
        return "(" + this.left + " " + this.sign + " " + this.right + ")";
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractOperation) {
            AbstractOperation o = (AbstractOperation) obj;
            return this.sign.equals(o.sign)
                    && this.left.equals(o.left)
                    && this.right.equals(o.right);
        }
        return false;
    }
}