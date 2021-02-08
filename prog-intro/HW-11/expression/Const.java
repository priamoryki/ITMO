package expression;

/**
 * @author Pavel Lymar
 */
public class Const implements AbstractComponent {
    private int x;

    public Const(int x) {
        this.x = x;
    }

    public int evaluate(int x) {
        return this.x;
    }

    public int evaluate(int x, int y, int z) {
        return this.x;
    }

    public String toString() {
        return Integer.toString(this.x);
    }

    public int hashCode() {
        return Integer.hashCode(this.x);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const c = (Const) obj;
            return this.x == c.x;
        }
        return false;
    }
}