package src.operations;

import java.util.Map;

/**
 * @author Pavel Lymar
 */
public abstract class AbstractOperation implements Component {
    private final Component left, right;

    public AbstractOperation(Component left, Component right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> vars) {
        return apply(left.evaluate(vars), right.evaluate(vars));
    }

    public abstract boolean apply(boolean a, boolean b);
}
