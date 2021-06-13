import java.util.Scanner;

public class TaskH {
    public static int res(int[] a, int x) {
        int val = 1;
        int t = 0;
        for (int i = 0; i < a.length; i++) {
            if (t + a[i] > x) {
                val++;
                t = 0;
                if (a[i] > x) {
                    return -1;
                }
            }
            t += a[i];
        }
        return val;
    }

    public static void main(String[] args) {
        int A = 0;
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scn.nextInt();
            A += a[i];
        }
        int q = scn.nextInt();
        int[] f = new int[A];
        int[] t = new int[q];
        for (int i = 0; i < n; i++) {
            t[i] = scn.nextInt();
        }
        scn.close();
        q = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < a[i]; j++) {
                f[q + j] = a[i];
            }
            q += a[i];
        }
    }
}