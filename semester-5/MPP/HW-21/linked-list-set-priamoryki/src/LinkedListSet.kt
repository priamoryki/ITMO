package mpp.linkedlistset

import kotlinx.atomicfu.atomic
import java.util.concurrent.atomic.AtomicMarkableReference

/**
 * This solution has been presented in "The Art of Multiprocessor Programming"
 */
class LinkedListSet<E : Comparable<E>> {
    private val first = Node<E>(prev = null, element = null, next = null)
    private val last = Node<E>(prev = first, element = null, next = null)

    init {
        first.next.set(last, false)
    }

    private val head = atomic(first)

    /**
     * Adds the specified element to this set
     * if it is not already present.
     *
     * Returns `true` if this set did not
     * already contain the specified element.
     */
    fun add(element: E): Boolean {
        while (true) {
            val window = find(element)
            val pred = window.prev.reference
            val curr = window.next.reference
            if (curr?.element == element) {
                return false
            }
            val node = Node(null, element, null)
            node.next = AtomicMarkableReference(curr, false)
            if (pred!!.next.compareAndSet(curr, node, false, false)) {
                return true
            }
        }
    }

    /**
     * Removes the specified element from this set
     * if it is present.
     *
     * Returns `true` if this set contained
     * the specified element.
     */
    fun remove(element: E): Boolean {
        while (true) {
            val window = find(element)
            val pred = window.prev.reference
            val curr = window.next.reference
            if (curr?.element != element) {
                return false
            }
            val succ = curr.next.reference
            if (!curr.next.attemptMark(succ, true)) {
                continue
            }
            pred!!.next.compareAndSet(curr, succ, false, false)
            return true
        }
    }

    /**
     * Returns `true` if this set contains
     * the specified element.
     */
    fun contains(element: E): Boolean {
        val marked = booleanArrayOf(false)
        var curr = head.value
        while (curr.element != null && curr.element!! < element) {
            curr = curr.next.reference ?: break
            curr.next.get(marked)
        }
        return curr.element == element && !marked[0]
    }

    private fun find(element: E): Node<E> {
        val marked = booleanArrayOf(false)
        retry@ while (true) {
            var pred = head.value
            var curr = pred.next.reference
            while (true) {
                var succ = curr!!.next.get(marked)
                while (marked[0]) {
                    if (!pred.next.compareAndSet(curr, succ, false, false)) {
                        continue@retry
                    }
                    curr = succ
                    succ = curr!!.next.get(marked)
                }
                if (curr?.element == null || curr.element!! >= element) {
                    return Node(pred, null, curr)
                }
                pred = curr
                curr = succ
            }
        }
    }
}

private class Node<E : Comparable<E>>(prev: Node<E>?, val element: E?, next: Node<E>?) {
    val prev = AtomicMarkableReference(prev, false)

    var next = AtomicMarkableReference(next, false)
}
