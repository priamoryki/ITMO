package expression;

import java.util.Objects;

/**
 * @author Pavel Lymar
 */
public class Variable implements AbstractComponent {
    private String var;

    Variable(String x) {
        this.var = x;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        return 0;
    }

    public String toString() {
        return this.var;
    }

    public int hashCode() {
        return Objects.hash(var);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable v = (Variable) obj;
            return var.equals(v.toString());
        }
        return false;
    }
}