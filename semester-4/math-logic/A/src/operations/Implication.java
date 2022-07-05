package src.operations;

/**
 * @author Pavel Lymar
 */
public class Implication extends AbstractOperation {
    public Implication(Component left, Component right) {
        super(left, right);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return !a || b;
    }
}
