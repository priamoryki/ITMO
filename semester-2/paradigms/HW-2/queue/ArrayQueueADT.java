package queue;

/**
 * @author Pavel Lymar
 */

// Model: [q[1], q[2], ..., q[|q|]], |q| -- Array queue size
// q' -- queue before operation
// q -- queue after operation
// Inv: q != null && |q| >= 0 && forall i = 1..|q|: q[i] != null
// Immutable: |q'| == |q| && forall i = 1..|q'|: q'[i] == q[i]
public class ArrayQueueADT {
    private Object[] elements = new Object[1];
    private int start = 0, end = 0;

    // Pre: q' != null
    // Post: |q| == 0
    public static void clear(ArrayQueueADT q) {
        q.elements = new Object[1];
        q.start = 0;
        q.end = 0;
    }

    // Pre: q' != null
    // Post: R == |q'| &&
    // Immutable
    public static int size(ArrayQueueADT q) {
        if (q.elements[q.end] == null) {
            return (q.end - q.start + q.elements.length) % q.elements.length;
        } else {
            return q.elements.length;
        }
    }

    // Pre: q' != null
    // Post: R == (|q'| == 0) &&
    // Immutable
    public static boolean isEmpty(ArrayQueueADT q) {
        return size(q) == 0;
    }

    // Pre: q' != null && x != null
    // Post: q[|q'| + 1] == x &&
    // |q| == |q'| + 1 &&
    // forall i = 1..|q'|: q'[i] == q[i]
    public static void enqueue(ArrayQueueADT q, Object x) {
        assert x != null : "Invalid operation for this argument: " + x.toString();
        if (size(q) == q.elements.length) {
            updateArray(q);
        }
        q.elements[q.end] = x;
        q.end = (q.end + 1) % q.elements.length;
    }

    // Pre: q' != null
    // Post: Immutable
    private static void updateArray(ArrayQueueADT q) {
        Object[] result = new Object[2 * q.elements.length];
        System.arraycopy(q.elements, q.start, result, 0, q.elements.length - q.start);
        System.arraycopy(q.elements, 0, result, q.elements.length - q.start, q.end);
        q.end = size(q);
        q.start = 0;
        q.elements = result;
    }

    // Pre: q' != null && |q'| > 0
    // Post: R == q'[1] &&
    // Immutable
    public static Object element(ArrayQueueADT q) {
        assert !isEmpty(q) : "Invalid operation for empty queue";
        return q.elements[q.start];
    }

    // Pre: q' != null && |q'| > 0
    // Post: R == q'[1] &&
    // |q| == |q'| - 1 &&
    // forall i = 2..|q'|: q'[i] == q[i]
    public static Object dequeue(ArrayQueueADT q) {
        Object result = element(q);
        q.elements[q.start] = null;
        q.start = (q.start + 1) % q.elements.length;
        return result;
    }

    // Pre: q' != null && x != null
    // Post: q[1] == x &&
    // |q| == |q'| + 1 &&
    // forall i = 2..|q'| + 1: q'[i - 1] == q[i]
    public static void push(ArrayQueueADT q, Object x) {
        assert x != null : "Invalid operation for this argument: " + x.toString();
        if (size(q) == q.elements.length) {
            updateArray(q);
        }
        q.elements[(q.start - 1 + q.elements.length) % q.elements.length] = x;
        q.start = (q.start - 1 + q.elements.length) % q.elements.length;
    }

    // Pre: q' != null && |q'| > 0
    // Post: R == q'[|q'|] &&
    // Immutable
    public static Object peek(ArrayQueueADT q) {
        assert !isEmpty(q) : "Invalid operation for empty queue";
        return q.elements[(q.end - 1 + q.elements.length) % q.elements.length];
    }

    // Pre: q' != null && |q'| > 0
    // Post: R == q'[|q'|] &&
    // |q| == |q'| - 1 &&
    // forall i = 1..|q'| - 1: q'[i] == q[i]
    public static Object remove(ArrayQueueADT q) {
        Object result = peek(q);
        q.elements[(q.end - 1 + q.elements.length) % q.elements.length] = null;
        q.end = (q.end - 1 + q.elements.length) % q.elements.length;
        return result;
    }
}