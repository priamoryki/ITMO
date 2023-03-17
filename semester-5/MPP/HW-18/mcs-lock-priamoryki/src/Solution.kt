import java.util.concurrent.atomic.AtomicReference

/**
 * @author Pavel Lymar
 */
class Solution(private val env: Environment) : Lock<Solution.Node> {
    private val tail = AtomicReference<Node?>(null)

    override fun lock(): Node {
        val my = Node()
        val prev = tail.getAndSet(my)
        if (prev != null) {
            prev.next.set(my)
            while (my.isLocked.get()) {
                env.park()
            }
        }
        return my
    }

    override fun unlock(node: Node) {
        if (node.next.get() == null) {
            if (tail.compareAndSet(node, null)) {
                return
            }
            while (node.next.get() == null) {
                // waiting
            }
        }
        val next = node.next.get() ?: return
        next.isLocked.set(false)
        env.unpark(next.thread)
    }

    class Node {
        val thread = Thread.currentThread()
        val isLocked = AtomicReference(true)
        val next = AtomicReference<Node?>(null)
    }
}
