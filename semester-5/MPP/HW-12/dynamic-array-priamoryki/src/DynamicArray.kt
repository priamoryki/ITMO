package mpp.dynamicarray

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls

interface DynamicArray<E> {
    /**
     * Returns the element located in the cell [index],
     * or throws [IllegalArgumentException] if [index]
     * exceeds the [size] of this array.
     */
    fun get(index: Int): E

    /**
     * Puts the specified [element] into the cell [index],
     * or throws [IllegalArgumentException] if [index]
     * exceeds the [size] of this array.
     */
    fun put(index: Int, element: E)

    /**
     * Adds the specified [element] to this array
     * increasing its [size].
     */
    fun pushBack(element: E)

    /**
     * Returns the current size of this array,
     * it increases with [pushBack] invocations.
     */
    val size: Int
}

class DynamicArrayImpl<E> : DynamicArray<E> {
    private val core = atomic(Core<E>(INITIAL_CAPACITY))

    override fun get(index: Int): E {
        require(index < size)
        val curHead = core.value
        val result = curHead.array[index].value
        require(result != null)
        return result
    }

    override fun put(index: Int, element: E) {
        require(index < size)
        var curHead = core.value
        while (true) {
            curHead.array[index].getAndSet(element)
            curHead = curHead.next.value ?: return
        }
    }

    override fun pushBack(element: E) {
        while (true) {
            val curHead = core.value
            val curSize = curHead.size.value
            if (curSize >= curHead.capacity) {
                var next: Core<E>? = Core(2 * curHead.capacity)
                if (!curHead.next.compareAndSet(null, next)) {
                    next = curHead.next.value
                }
                if (next != null) {
                    copyAndSetNewCore(curHead, next)
                }
                continue
            }
            val flag = curHead.array[curSize].compareAndSet(null, element)
            curHead.size.compareAndSet(curSize, curSize + 1)
            if (flag) {
                return
            }
        }
    }

    private fun copyAndSetNewCore(curHead: Core<E>, next: Core<E>) {
        for (i in 0 until curHead.capacity) {
            next.array[i].compareAndSet(null, curHead.array[i].value)
        }
        core.compareAndSet(curHead, next)
    }

    override val size: Int get() = core.value.size.value
}

private class Core<E>(
    val capacity: Int
) {
    val size = atomic(capacity / 2)
    val array = atomicArrayOfNulls<E>(capacity)
    val next: AtomicRef<Core<E>?> = atomic(null)
}

private const val INITIAL_CAPACITY = 1 // DO NOT CHANGE ME