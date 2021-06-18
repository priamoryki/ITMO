package search;

/**
 * @author Pavel Lymar
 */
public class BinarySearch {
    // Pre: farall i = 1..args.length - 2: args[i] >= args[i + 1]
    // Post: R == (min i: 0 < i < args.length && args[i] <= args[0])
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(iterativeBinarySearch(x, a));
        // System.out.println(recursiveBinarySearch(x, a, -1, a.length));
    }

    // Pre: forall i, j = 0..a.length - 1: i < j a[j] <= a[i]
    // Post: R == (min i: a[i] <= x)
    public static int iterativeBinarySearch(int x, int[] a) {
        int l = -1, r = a.length;
        // l == -1 && r == a.length
        // Inv: r' - l' > 1 && a[r'] <= x < a[l'], assuming that a[-1] == +inf, a[a.length] = -inf
        while (r - l != 1) {
            int m = (l + r) / 2;
            // m == (l + r) / 2
            if (a[m] > x) {
                // Pre: a[m] > x && a[r] <= x < a[l]
                l = m;
                // Post: l == m && a[r] <= x < a[l]
            } else {
                // Pre: a[m] <= x && a[r] <= x < a[l]
                r = m;
                // Post: r == m && a[r] <= x < a[l]
            }
            // Inv == true
        }
        // r == l + 1 && a[r] <= x < a[l]
        return r;
    }

    // Pre: forall i, j = 0..a.length - 1: i < j a[j] <= a[i]
    // Post: R == (min i: a[i] <= x)
    // Inv: r' - l' >= 1 && a[r'] <= x < a[l'], assuming that a[-1] == +inf, a[a.length] = -inf
    public static int recursiveBinarySearch(int x, int[] a, int l, int r) {
        if (r - l == 1) {
            // r == l + 1 && a[r] <= x < a[l]
            return r;
        }
        int m = (l + r) / 2;
        // m == (l + r) / 2 && r - l > 1 && a[r] <= x < a[l]
        if (a[m] > x) {
            // (r - m == (r - l) / 2 && r - l > 1) -> r - m >= 1
            // Pre: a[m] > x && a[r] <= x && r - m >= 1
            // Post: a[r] <= x < a[m] && r - m >= 1
            return recursiveBinarySearch(x, a, m, r);
        } else {
            // (m - l == (r - l) / 2 && r - l > 1) -> m - l >= 1
            // Pre: a[m] <= x && x < a[l] && m - l >= 1
            // Post: a[m] <= x < a[l] && m - l >= 1
            return recursiveBinarySearch(x, a, l, m);
        }
    }
}