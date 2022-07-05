package src.operations;

/**
 * @author Pavel Lymar
 */
public class And extends AbstractOperation {
    public And(Component left, Component right) {
        super(left, right);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return a && b;
    }
}
