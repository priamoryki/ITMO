package mpp.faaqueue

import kotlinx.atomicfu.*

class FAAQueue<E> {
    private val head: AtomicRef<Segment> // Head pointer, similarly to the Michael-Scott queue (but the first node is _not_ sentinel)
    private val tail: AtomicRef<Segment> // Tail pointer, similarly to the Michael-Scott queue

    init {
        val firstNode = Segment()
        head = atomic(firstNode)
        tail = atomic(firstNode)
    }

    /**
     * Adds the specified element [element] to the queue.
     */
    fun enqueue(element: E) {
        while (true) {
            val curTail = tail.value
            val id = curTail.enqIdx.getAndIncrement()
            if (id < curTail.elements.size && curTail.elements[id].compareAndSet(null, element)) {
                return
            }
            val isEmpty = curTail.next.compareAndSet(null, Segment(element))
            tail.compareAndSet(curTail, curTail.next.value!!)
            if (isEmpty) {
                return
            }
        }
    }

    /**
     * Retrieves the first element from the queue and returns it;
     * returns `null` if the queue is empty.
     */
    fun dequeue(): E? {
        while (true) {
            val curHead = head.value
            val id = curHead.deqIdx.getAndIncrement()
            if (id >= curHead.elements.size) {
                val nextHead = curHead.next.value ?: return null
                head.compareAndSet(curHead, nextHead)
                continue
            }
            val result = curHead.elements[id].getAndSet(BROKEN) ?: continue
            return result as E?
        }
    }

    /**
     * Returns `true` if this queue is empty, or `false` otherwise.
     */
    val isEmpty: Boolean
        get() {
            return head.value.next.value != null
        }
}

private class Segment {
    val next: AtomicRef<Segment?> = atomic(null)
    val enqIdx = atomic(0)
    val deqIdx = atomic(0)
    val elements = atomicArrayOfNulls<Any>(SEGMENT_SIZE)

    constructor()

    constructor(element: Any?) {
        enqIdx.incrementAndGet()
        elements[0].getAndSet(element)
    }

    private fun get(i: Int) = elements[i].value

    private fun cas(i: Int, expect: Any?, update: Any?) = elements[i].compareAndSet(expect, update)

    private fun put(i: Int, value: Any?) {
        elements[i].value = value
    }
}

private val BROKEN = Any()
const val SEGMENT_SIZE = 2 // DO NOT CHANGE, IMPORTANT FOR TESTS
