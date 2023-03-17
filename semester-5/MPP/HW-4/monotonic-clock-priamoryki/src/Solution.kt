/**
 * В теле класса решения разрешено использовать только переменные делегированные в класс RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author Lymar Pavel
 */
class Solution : MonotonicClock {
    private var c1 by RegularInt(0)
    private var c2 by RegularInt(0)
    private var c3 by RegularInt(0)
    private var w1 by RegularInt(0)
    private var w2 by RegularInt(0)

    override fun write(time: Time) {
        // write right-to-left
        c1 = time.d1
        c2 = time.d2
        c3 = time.d3
        w1 = time.d1
        w2 = time.d2
    }

    override fun read(): Time {
        // read left-to-right
        val l1 = w1
        val l2 = w2
        val v3 = c3
        val v1 = c1
        val v2 = c2
        return Time(v1, if (l1 == v1) v2 else 0, if (l1 == v1 && l2 == v2) v3 else 0)
    }
}