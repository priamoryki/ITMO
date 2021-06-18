package queue;

/**
 * @author Pavel Lymar
 */

// Model: [a[1], a[2], ..., a[|a|]], |a| -- Array queue size
// a' -- queue before operation
// a -- queue after operation
// Inv: |a| >= 0 && forall i = 1..|a|: a[i] != null
// Immutable: |a'| == |a| && forall i = 1..|a'|: a'[i] == a[i]
public class ArrayQueueModule {
    private static Object[] elements = new Object[1];
    private static int start = 0, end = 0;

    // Pre: true
    // Post: |a| == 0
    public static void clear() {
        elements = new Object[1];
        start = 0;
        end = 0;
    }

    // Pre: true
    // Post: R == |a'| && Immutable
    public static int size() {
        if (elements[end] == null) {
            return (end - start + elements.length) % elements.length;
        } else {
            return elements.length;
        }
    }

    // Pre: true
    // Post: R == (|a'| == 0) &&
    // Immutable
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pre: x != null
    // Post: a[|a'| + 1] == x &&
    // |a'| == |a| + 1 &&
    // forall i = 1..|a'|: a'[i] == a[i]
    public static void enqueue(Object x) {
        assert x != null : "Invalid operation for this argument: " + x.toString();
        if (size() == elements.length) {
            updateArray();
        }
        elements[end] = x;
        end = (end + 1) % elements.length;
    }

    // Pre: true
    // Post: Immutable
    private static void updateArray() {
        Object[] result = new Object[2 * elements.length];
        System.arraycopy(elements, start, result, 0, elements.length - start);
        System.arraycopy(elements, 0, result, elements.length - start, end);
        end = size();
        start = 0;
        elements = result;
    }

    // Pre: |a'| > 0
    // Post: R == a'[1] &&
    // Immutable
    public static Object element() {
        assert !isEmpty() : "Invalid operation for empty queue";
        return elements[start];
    }

    // Pre: |a'| > 0
    // Post: R == a'[1] &&
    // |a| == |a'| - 1 &&
    // forall i = 2..|a'|: a'[i] == a[i]
    public static Object dequeue() {
        Object result = element();
        elements[start] = null;
        start = (start + 1) % elements.length;
        return result;
    }

    // Pre: x != null
    // Post: a[1] == x &&
    // |a| == |a'| + 1 &&
    // forall i = 2..|a'| + 1: a'[i - 1] == a[i]
    public static void push(Object x) {
        assert x != null : "Invalid operation for this argument: " + x.toString();
        if (size() == elements.length) {
            updateArray();
        }
        elements[(start - 1 + elements.length) % elements.length] = x;
        start = (start - 1 + elements.length) % elements.length;
    }

    // Pre: |a'| > 0
    // Post: R == a'[|a'|] &&
    // Immutable
    public static Object peek() {
        assert !isEmpty() : "Invalid operation for empty queue";
        return elements[(end - 1 + elements.length) % elements.length];
    }

    // Pre: |a'| > 0
    // Post: R == a'[|a'|] &&
    // |a| == |a'| - 1 &&
    // forall i = 1..|a'| - 1: a'[i] == a[i]
    public static Object remove() {
        Object result = peek();
        elements[(end - 1 + elements.length) % elements.length] = null;
        end = (end - 1 + elements.length) % elements.length;
        return result;
    }
}