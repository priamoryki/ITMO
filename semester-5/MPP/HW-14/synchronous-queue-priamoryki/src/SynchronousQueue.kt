import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * An element is transferred from sender to receiver only when [send] and [receive]
 * invocations meet in time (rendezvous), so [send] suspends until another coroutine
 * invokes [receive] and [receive] suspends until another coroutine invokes [send].
 */
class SynchronousQueue<E> {
    private val head: AtomicRef<Node<E>>
    private val tail: AtomicRef<Node<E>>

    init {
        val dummy = Send<E>(null)
        head = atomic(dummy)
        tail = atomic(dummy)
    }

    /**
     * Sends the specified [element] to this channel, suspending if there is no waiting
     * [receive] invocation on this channel.
     */
    suspend fun send(element: E) {
        while (true) {
            val curHead = head.value
            val curTail = tail.value
            val node: Node<E> = Send(element)
            if ((curTail is Send<E> || curHead == curTail) && check(curTail, node)) {
                return
            }
            if (curTail is Send<E> || curHead == curTail) {
                continue
            }
            val next = curHead.next.value ?: continue
            if (next is Receive<E> && next.continuation != null && head.compareAndSet(curHead, next)) {
                next.value.compareAndSet(null, element)
                next.continuation!!.resume(true)
                return
            }
        }
    }

    /**
     * Retrieves and removes an element from this channel if there is a waiting [send] invocation on it,
     * suspends the caller if this channel is empty.
     */
    suspend fun receive(): E {
        while (true) {
            val curHead = head.value
            val curTail = tail.value
            val node: Node<E> = Receive(null)
            if ((curTail is Receive<E> || curHead == curTail) && check(curTail, node)) {
                return node.value.value!!
            }
            if (curTail is Receive<E> || curHead == curTail) {
                continue
            }
            val next = curHead.next.value ?: continue
            val element = next.value.value ?: continue
            if (next is Send<E> && next.continuation != null && head.compareAndSet(curHead, next)) {
                next.value.compareAndSet(element, null)
                next.continuation!!.resume(true)
                return element
            }
        }
    }

    private suspend fun check(curTail: Node<E>, node: Node<E>): Boolean {
        return suspendCoroutine sc@{ cont ->
            node.continuation = cont
            if (curTail.next.compareAndSet(null, node)) {
                tail.compareAndSet(curTail, node)
                return@sc
            }
            tail.compareAndSet(curTail, curTail.next.value!!)
            cont.resume(false)
        }
    }
}

private open class Node<E>(v: E?) {
    val value = atomic(v)
    val next: AtomicRef<Node<E>?> = atomic(null)
    var continuation: Continuation<Boolean>? = null
}

private class Send<E>(v: E?) : Node<E>(v)

private class Receive<E>(v: E?) : Node<E>(v)
