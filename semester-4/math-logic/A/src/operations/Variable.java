package src.operations;

import java.util.Map;

/**
 * @author Pavel Lymar
 */
public class Variable implements Component {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> vars) {
        return vars.get(name);
    }
}
