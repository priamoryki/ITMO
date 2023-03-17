import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class FCPriorityQueue<E : Comparable<E>> {
    private val q = PriorityQueue<E>()
    private val lock = atomic(false)
    private val actions = atomicArrayOfNulls<ActionWrapper<E>?>(4 * Runtime.getRuntime().availableProcessors())

    private fun tryLock(): Boolean {
        return lock.compareAndSet(expect = false, update = true)
    }

    private fun unlock() {
        lock.compareAndSet(expect = true, update = false)
    }

    /**
     * Retrieves the element with the highest priority
     * and returns it as the result of this function;
     * returns `null` if the queue is empty.
     */
    fun poll(): E? {
        return operation(ActionWrapper(true, null) {
            q.poll()
        })
    }

    /**
     * Returns the element with the highest priority
     * or `null` if the queue is empty.
     */
    fun peek(): E? {
        return operation(ActionWrapper(true, null) {
            q.peek()
        })
    }

    /**
     * Adds the specified element to the queue.
     */
    fun add(element: E) {
        operation(ActionWrapper(true, null) {
            q.add(element)
            null
        })
    }

    private fun combine() {
        for (id in 0 until actions.size) {
            val curAction = actions[id].value ?: continue
            if (curAction.isActive) {
                actions[id].compareAndSet(curAction, ActionWrapper(false, curAction.action(), curAction.action))
            }
        }
    }

    private fun operation(actionWrapper: ActionWrapper<E>): E? {
        if (tryLock()) {
            actionWrapper.result = actionWrapper.action()
            combine()
            return actionWrapper.result.also { unlock() }
        }

        var id = (ThreadLocalRandom.current().nextInt(actions.size) + actions.size) % actions.size
        while (!actions[id].compareAndSet(null, actionWrapper)) {
            id = (id + 1) % actions.size
        }

        while (true) {
            val curAction = actions[id].value ?: continue
            if (!curAction.isActive) {
                actions[id].compareAndSet(curAction, null)
                return curAction.result
            }
            if (tryLock()) {
                combine().also { unlock() }
            }
        }
    }
}

private data class ActionWrapper<E>(val isActive: Boolean, var result: E?, val action: () -> E?)
