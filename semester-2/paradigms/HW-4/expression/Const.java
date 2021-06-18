package expression;

/**
 * @author Pavel Lymar
 */
public class Const<T> implements AbstractComponent<T> {
    private T x;

    public Const(T x) {
        this.x = x;
    }

    public T evaluate(T x) {
        return this.x;
    }

    public T evaluate(T x, T y, T z) {
        return this.x;
    }
}