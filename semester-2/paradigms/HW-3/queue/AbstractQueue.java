package queue;

/**
 * @author Pavel Lymar
 */

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    protected abstract void ownEnqueue(Object x);

    protected abstract Object ownElement();

    protected abstract void ownDequeue();

    protected abstract void ownClear();

    public void clear() {
        ownClear();
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(final Object x) {
        // :NOTE: NPE
        assert x != null : "Invalid operation for this argument";
        ownEnqueue(x);
        size++;
    }

    public Object element() {
        assert !isEmpty() : "Invalid operation for empty queue";
        return ownElement();
    }

    public Object dequeue() {
        final Object result = element();
        ownDequeue();
        size--;
        return result;
    }

    public boolean contains(final Object element) {
        return firstOccurrence(element, false);
    }

    public boolean removeFirstOccurrence(final Object element) {
        return firstOccurrence(element, true);
    }

    private boolean firstOccurrence(final Object element, final boolean isRemove) {

        boolean result = false;
        final int s = size();
        for (int i = 0; i < s; i++) {
            // :NOTE: Упростить
            Object e = dequeue();
            if (e.equals(element) && !result) {
                result = true;
                if (isRemove) {
                    continue;
                }
            }
            enqueue(e);
        }
        return result;
    }
}