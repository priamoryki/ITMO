package queue;

/**
 * @author Pavel Lymar
 */

public class Node {
    private Object value;
    private Node next;

    Node(Object value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Object getValue() {
        return this.value;
    }

    public Node getNext() {
        return this.next;
    }
}