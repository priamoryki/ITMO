package src.operations;

import java.util.Map;

/**
 * @author Pavel Lymar
 */
public interface Component {
    boolean evaluate(Map<String, Boolean> vars);
}
