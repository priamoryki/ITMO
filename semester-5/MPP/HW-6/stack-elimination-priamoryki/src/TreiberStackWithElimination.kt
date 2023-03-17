package mpp.stackWithElimination

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls
import java.util.concurrent.ThreadLocalRandom

class TreiberStackWithElimination<E> {
    private val top = atomic<Node<E>?>(null)
    private val eliminationArray = atomicArrayOfNulls<E?>(ELIMINATION_ARRAY_SIZE)

    /**
     * Adds the specified element [x] to the stack.
     */
    fun push(x: E) {
        if (tryPush(x)) {
            return
        }

        var newHead: Node<E>?
        var oldHead: Node<E>?
        do {
            oldHead = top.value
            newHead = Node(x, oldHead)
        } while (!top.compareAndSet(oldHead, newHead))
    }

    private fun tryPush(x: E): Boolean {
        val id = pushInArray(x)
        if (id == -1) {
            return false
        }

        for (i in 0 until ELIMINATION_WAIT_TIME) {
            if (eliminationArray[id].compareAndSet(null, null)) {
                return true
            }
        }

        return !eliminationArray[id].compareAndSet(x, null)
    }

    private fun pushInArray(x: E): Int {
        val randId = ThreadLocalRandom.current().nextInt(eliminationArray.size)
        for (i in randId until randId + eliminationArray.size) {
            val curId = i % eliminationArray.size
            if (eliminationArray[curId].compareAndSet(null, x)) {
                return curId
            }
        }
        return -1
    }

    /**
     * Retrieves the first element from the stack
     * and returns it; returns `null` if the stack
     * is empty.
     */
    fun pop(): E? {
        val popped = tryPop()
        if (popped != null) {
            return popped
        }

        var newHead: Node<E>?
        var oldHead: Node<E>?
        do {
            oldHead = top.value
            if (oldHead == null) {
                return null
            }
            newHead = oldHead.next
        } while (!top.compareAndSet(oldHead, newHead))
        return oldHead?.x
    }

    private fun tryPop(): E? {
        val randId = ThreadLocalRandom.current().nextInt(eliminationArray.size)
        for (i in randId until randId + eliminationArray.size) {
            val curId = i % eliminationArray.size
            for (j in 0 until ELIMINATION_WAIT_TIME) {
                val result = eliminationArray[curId].getAndSet(null)
                if (result != null) {
                    return result
                }
            }
        }
        return null
    }
}

private class Node<E>(val x: E, val next: Node<E>?)

private const val ELIMINATION_WAIT_TIME = 1
private const val ELIMINATION_ARRAY_SIZE = 2 // DO NOT CHANGE IT