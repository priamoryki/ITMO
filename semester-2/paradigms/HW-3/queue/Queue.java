package queue;

/**
 * @author Pavel Lymar
 */

// Model: [a[1], a[2], ..., a[|a|]], |a| -- Array queue size
// a' -- queue before operation
// a -- queue after operation
// Inv: |a| >= 0 && forall i = 1..|a|: a[i] != null
// Immutable: |a'| == |a| && forall i = 1..|a'|: a'[i] == a[i]
public interface Queue {
    // Pre: true
    // Post: |a| == 0
    void clear();

    // Pre: true
    // Post: R == |a'| && Immutable
    int size();

    // Pre: true
    // Post: R == (|a'| == 0) &&
    // Immutable
    boolean isEmpty();

    // Pre: x != null
    // Post: a[|a'| + 1] == x &&
    // |a'| == |a| + 1 &&
    // forall i = 1..|a'|: a'[i] == a[i]
    void enqueue(Object x);

    // Pre: |a'| > 0
    // Post: R == a'[1] &&
    // Immutable
    Object element();

    // Pre: |a'| > 0
    // Post: R == a'[1] &&
    // |a| == |a'| - 1 &&
    // forall i = 2..|a'|: a'[i] == a[i]
    Object dequeue();

    // Pre: true
    // Post: Immutable &&
    // R == (exists i: 1 <= i <= |a| && a[i] == element)
    boolean contains(Object element);

    // :NOTE: min i
    // Pre: element != null
    // Let min x -- such m in x: forall i in x: (i <= m -> i == m)
    // Let a'[|a'| + 1] == element
    // Let id = min i: 1 <= i <= |a'| + 1 && a'[i] == element
    // Post: R == (id <= |a'|) &&
    // (|a| == |a'| - 1 + max(0, |a'| - id)) &&
    // forall 1 <= i < id: a[i] == a'[i] && forall id <= i < |a'|: a[i] == a'[i + 1]
    boolean removeFirstOccurrence(Object element);
}