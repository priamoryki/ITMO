package dijkstra

import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.Phaser
import kotlin.concurrent.thread
import kotlin.random.Random

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 -> Integer.compare(o1!!.distance, o2!!.distance) }

// Returns `Integer.MAX_VALUE` if a path has not been found.
fun shortestPathParallel(start: Node) {
    val workers = Runtime.getRuntime().availableProcessors()
    // The distance to the start node is `0`
    start.distance = 0
    // Create a priority (by distance) queue and add the start node into it
    val q = DijkstraQueue(workers)
    q.add(start)
    // Run worker threads and wait until the total work is done
    val onFinish = Phaser(workers + 1) // `arrive()` should be invoked at the end by each worker
    repeat(workers) {
        thread {
            while (true) {
                val node = q.get() ?: if (q.isEmpty()) break else continue
                for (edge in node.outgoingEdges) {
                    while (true) {
                        val old = edge.to.distance
                        val updated = node.distance + edge.weight
                        if (updated < old) {
                            if (edge.to.casDistance(old, updated)) {
                                q.add(edge.to)
                            } else {
                                continue
                            }
                        }
                        break
                    }
                }
                q.decrementSize()
            }
            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}

private class DijkstraQueue(workers: Int) {
    private val size = atomic(0)
    private val queues: MutableList<PriorityQueue<Node>> = Collections.nCopies(
        workers,
        PriorityQueue(NODE_DISTANCE_COMPARATOR)
    )

    fun get(): Node? {
        val from = Random.nextInt(queues.size)
        val to = (from + 1) % queues.size
        synchronized(queues[from]) {
            synchronized(queues[to]) {
                if (queues[from].peek() != null) {
                    if (queues[to].peek() != null) {
                        return if (queues[from].peek().distance < queues[to].peek().distance) {
                            queues[from].poll()
                        } else {
                            queues[to].poll()
                        }
                    }
                    return queues[from].peek()
                }
                return queues[to].peek()
            }
        }
    }

    fun add(element: Node) {
        val randomizedIndex = Random.nextInt(queues.size)
        synchronized(queues[randomizedIndex]) {
            queues[randomizedIndex].add(element)
        }
        size.incrementAndGet()
    }

    fun isEmpty(): Boolean {
        return size.compareAndSet(0, 0)
    }

    fun decrementSize() {
        size.decrementAndGet()
    }
}
