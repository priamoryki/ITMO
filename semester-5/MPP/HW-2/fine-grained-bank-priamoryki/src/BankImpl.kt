import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Bank implementation.
 *
 * :TODO: This implementation has to be made thread-safe.
 *
 * @author :TODO: LastName FirstName
 */
class BankImpl(n: Int) : Bank {
    private val accounts: Array<Account> = Array(n) { Account() }

    override val numberOfAccounts: Int
        get() = accounts.size

    /**
     * :TODO: This method has to be made thread-safe.
     */
    override fun getAmount(index: Int): Long {
        val acc = accounts[index]
        return acc.lock.withLock { acc.amount }
    }

    /**
     * :TODO: This method has to be made thread-safe.
     */
    override val totalAmount: Long
        get() {
            accounts.forEach { acc -> acc.lock.lock() }
            val result = accounts.sumOf { account ->
                account.amount
            }
            accounts.forEach { acc -> acc.lock.unlock() }
            return result
        }

    /**
     * :TODO: This method has to be made thread-safe.
     */
    override fun deposit(index: Int, amount: Long): Long {
        require(amount > 0) { "Invalid amount: $amount" }
        val acc = accounts[index]
        acc.lock.withLock {
            check(!(amount > Bank.MAX_AMOUNT || acc.amount + amount > Bank.MAX_AMOUNT)) { "Overflow" }
            acc.amount += amount
            return acc.amount
        }
    }

    /**
     * :TODO: This method has to be made thread-safe.
     */
    override fun withdraw(index: Int, amount: Long): Long {
        require(amount > 0) { "Invalid amount: $amount" }
        val acc = accounts[index]
        acc.lock.withLock {
            check(acc.amount - amount >= 0) { "Underflow" }
            acc.amount -= amount
            return acc.amount
        }
    }

    /**
     * :TODO: This method has to be made thread-safe.
     */
    override fun transfer(fromIndex: Int, toIndex: Int, amount: Long) {
        require(amount > 0) { "Invalid amount: $amount" }
        require(fromIndex != toIndex) { "fromIndex == toIndex" }
        val (min, max) = fromIndex.coerceAtMost(toIndex) to fromIndex.coerceAtLeast(toIndex)
        accounts[min].lock.withLock {
            accounts[max].lock.withLock {
                val from = accounts[fromIndex]
                val to = accounts[toIndex]
                check(amount <= from.amount) { "Underflow" }
                check(!(amount > Bank.MAX_AMOUNT || to.amount + amount > Bank.MAX_AMOUNT)) { "Overflow" }
                from.amount -= amount
                to.amount += amount
            }
        }
    }

    /**
     * Private account data structure.
     */
    class Account {
        /**
         * Amount of funds in this account.
         */
        var amount: Long = 0

        /**
         * Account lock
         */
        val lock: ReentrantLock = ReentrantLock()
    }
}