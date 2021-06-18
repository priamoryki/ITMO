package queue;

/**
 * @author Pavel Lymar
 */

public class LinkedQueue extends AbstractQueue {
    private Node start, end;

    @Override
    public void ownClear() {
        start = null;
        end = null;
    }

    @Override
    public void ownEnqueue(final Object x) {
        final Node newElement = new Node(x);
        if (!isEmpty()) {
            end.setNext(newElement);
        } else {
            start = newElement;
        }
        end = newElement;
    }

    @Override
    public Object ownElement() {
        return start.getValue();
    }

    @Override
    public void ownDequeue() {
        start = start.getNext();
    }
}
