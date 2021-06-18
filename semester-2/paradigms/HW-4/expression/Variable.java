package expression;

/**
 * @author Pavel Lymar
 */
public class Variable<T> implements AbstractComponent<T> {
    private String var;

    public Variable(String x) {
        this.var = x;
    }

    public T evaluate(T x) {
        return x;
    }

    public T evaluate(T x, T y, T z) {
        switch (var) {
            case "x":
                return x;
            case "y":
                return y;
        }
        return z;
    }
}