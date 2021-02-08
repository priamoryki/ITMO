package expression;

/**
 * @author Pavel Lymar
 */
public abstract class AbstractUnaryOperation implements AbstractComponent {
	private final AbstractComponent component;
    private final String operator;

    public AbstractUnaryOperation(AbstractComponent c, String operator) {
        this.component = c;
        this.operator = operator;
    }

    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    public int evaluate(int x, int y, int z) {
        return apply(this.component.evaluate(x, y, z));
    }

    public abstract int apply(int a);

    public String toString() {
        return "(" + this.operator + " " + this.component + ")";
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractUnaryOperation) {
        	AbstractUnaryOperation o = (AbstractUnaryOperation) obj;
            return this.operator.equals(o.operator)
                    && this.component.equals(o.component);
        }
        return false;
    }
}