package queue;

/**
 * @author Pavel Lymar
 */

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[1];
    private int start = 0;

    // Pre: true
    // Post: |a| == 0
    @Override
    public void ownClear() {
        elements = new Object[1];
        start = 0;
    }

    @Override
    public void ownEnqueue(Object x) {
        if (size() == elements.length) {
            updateArray();
        }
        elements[(start + size()) % elements.length] = x;
    }

    private void updateArray() {
        Object[] result = new Object[2 * elements.length];
        System.arraycopy(elements, start, result, 0, elements.length - start);
        System.arraycopy(elements, 0, result, elements.length - start, (start + size()) % elements.length);
        start = 0;
        elements = result;
    }

    @Override
    public Object ownElement() {
        return elements[start];
    }

    @Override
    public void ownDequeue() {
        elements[start] = null;
        start = (start + 1) % elements.length;
    }
}