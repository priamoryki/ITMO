package src.operations;

import java.util.Map;

/**
 * @author Pavel Lymar
 */
public abstract class UnaryOperation implements Component {
    private final Component left;

    public UnaryOperation(Component left) {
        this.left = left;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> vars) {
        return apply(left.evaluate(vars));
    }

    public abstract boolean apply(boolean a);
}
