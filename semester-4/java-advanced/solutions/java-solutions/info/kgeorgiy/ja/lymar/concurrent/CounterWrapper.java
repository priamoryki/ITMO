package info.kgeorgiy.ja.lymar.concurrent;

/**
 * @author Pavel Lymar
 */
public class CounterWrapper {
    private final int aimNumber;
    private int counter;

    CounterWrapper(int aimNumber) {
        this.aimNumber = aimNumber;
    }

    public boolean isCompleted() {
        return counter >= aimNumber;
    }

    public void increase() {
        counter++;
    }
}
