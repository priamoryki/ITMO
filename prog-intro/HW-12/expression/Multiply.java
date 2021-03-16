package expression;

/**
 * @author Pavel Lymar
 */
public class Multiply extends AbstractOperation {
    public Multiply(AbstractComponent c1, AbstractComponent c2) {
        super(c1, c2, "*");
    }

    @Override
    public int apply(int a, int b) {
        return a * b;
    }
}