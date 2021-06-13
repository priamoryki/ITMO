import java.util.Scanner;
import java.util.Arrays;

public class TaskC {
    static long merge(int[] a, int[] l, int[] r) {
        int i = 0;
        int j = 0; 
        long res = 0;
        while (i < l.length || j < r.length) {
            if (i == l.length) {
                a[i + j] = r[j++];
            } else if (j == r.length) {
                a[i + j] = l[i++];
            } else if (l[i] <= r[j]) {
                a[i + j] = l[i++];          
            } else {
                a[i + j] = r[j++];
                res += l.length - i;
            }
        }
        return res;
    }
    
    static long invCount(int[] a) {
        if (a.length < 2) {
            return 0;
        }
        int l[] = Arrays.copyOfRange(a, 0, (a.length + 1) / 2);
        int r[] = Arrays.copyOfRange(a, (a.length + 1) / 2, a.length);
        return invCount(l) + invCount(r) + merge(a, l, r);
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scn.nextInt();
        }
        scn.close();
        System.out.println(invCount(a));
    }
}