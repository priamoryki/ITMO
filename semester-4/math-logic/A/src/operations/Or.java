package src.operations;

/**
 * @author Pavel Lymar
 */
public class Or extends AbstractOperation {
    public Or(Component left, Component right) {
        super(left, right);
    }

    @Override
    public boolean apply(boolean a, boolean b) {
        return a || b;
    }
}
