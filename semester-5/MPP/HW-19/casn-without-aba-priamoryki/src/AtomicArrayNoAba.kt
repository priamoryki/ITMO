import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.loop

private enum class Status {
    UNKNOWN,
    FAILED,
    SUCCESS,
}

private abstract class Descriptor {
    abstract fun complete()
}

private class Ref<T>(initialValue: T) {
    val v = atomic<Any?>(initialValue)

    var value: T
        set(value) {
            v.loop { cur ->
                if (cur is Descriptor) {
                    cur.complete()
                } else if (v.compareAndSet(cur, value)) {
                    return
                }
            }
        }
        get() {
            v.loop { cur ->
                if (cur is Descriptor) {
                    cur.complete()
                } else {
                    return cur as T
                }
            }
        }

    fun cas(expected: Any?, update: Any?): Any? {
        v.loop { cur ->
            if (cur == expected) {
                if (v.compareAndSet(cur, update)) {
                    return cur
                }
            } else {
                return cur
            }
        }
    }

    fun defaultCompareAndSet(expected: Any?, update: Any?): Boolean {
        return v.compareAndSet(expected, update)
    }
}

private class CAS2Descriptor<A, B>(
    val index1: Ref<A>, val expected1: A, val update1: A,
    val index2: Ref<B>, val expected2: B, val update2: B,
) : Descriptor() {
    val status = atomic(Status.UNKNOWN)

    override fun complete() {
        val newStatus = if (index2.cas(expected2, this) != expected2) Status.FAILED else Status.SUCCESS
        status.compareAndSet(Status.UNKNOWN, newStatus)
        if (status.value == Status.FAILED) {
            index1.v.compareAndSet(this, expected1)
            index2.v.compareAndSet(this, expected2)
            return
        }
        status.compareAndSet(Status.UNKNOWN, Status.SUCCESS)
        index1.v.compareAndSet(this, update1)
        index2.v.compareAndSet(this, update2)
    }
}

class AtomicArrayNoAba<E>(size: Int, initialValue: E) {
    private val array: Array<Ref<E>> = Array(size) { Ref(initialValue) }

    fun get(index: Int) =
        array[index].value

    fun cas(index: Int, expected: E, update: Any?) =
        array[index].defaultCompareAndSet(expected, update)

    fun cas2(
        index1: Int, expected1: E, update1: E,
        index2: Int, expected2: E, update2: E
    ): Boolean {
        if (index1 == index2 && expected1 is Int) {
            return cas(index1, expected1, expected1 + 2)
        }
        if (index1 == index2 && expected1 == expected2) {
            return cas(index1, expected1, update1)
        }
        if (index1 == index2) {
            return false
        }
        if (index1 > index2) {
            return cas2(index2, expected2, update2, index1, expected1, update1)
        }

        val descriptor = CAS2Descriptor(array[index1], expected1, update1, array[index2], expected2, update2)
        if (cas(index1, expected1, descriptor)) {
            descriptor.complete()
        }
        return descriptor.status.value == Status.SUCCESS
    }
}
