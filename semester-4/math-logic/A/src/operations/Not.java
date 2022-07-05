package src.operations;

/**
 * @author Pavel Lymar
 */
public class Not extends UnaryOperation {
    public Not(Component left) {
        super(left);
    }

    @Override
    public boolean apply(boolean a) {
        return !a;
    }
}
