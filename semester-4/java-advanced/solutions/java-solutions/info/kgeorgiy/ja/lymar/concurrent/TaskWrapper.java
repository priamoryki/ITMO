package info.kgeorgiy.ja.lymar.concurrent;

/**
 * @author Pavel Lymar
 */
public class TaskWrapper {
    private final Runnable task;
    private final CounterWrapper counter;

    public TaskWrapper(Runnable task, CounterWrapper counter) {
        this.task = task;
        this.counter = counter;
    }

    public CounterWrapper getCounter() {
        return counter;
    }

    public void run() {
        task.run();
    }
}
