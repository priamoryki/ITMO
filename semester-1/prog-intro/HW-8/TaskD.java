import java.util.Scanner;

public class TaskD {
    public static int R(int n, int k) {
        int res = 0;
        for (int l = 0; l < n; l++) {
            res += Math.pow(k, Math.ceil(l/2) + Math.ceil((n - l)/2));
            res %= 998_244_353;
        }
        return res;
    }

    public static int D(int n, int k) {
        int res = 0;
        for (int l = 1; l < n; l++) {
            if (n % l == 0) {
                res += ((n/l) * D(l, k));
                res %= 998_244_353;
            }
        }
        return (R(n, k) - res + 998_244_353) % 998_244_353;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int k = scn.nextInt();
        scn.close();

        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int l = 1; l <= n; l++) {
                if (i % l == 0) {
                    res += D(l, k);
                    res %= 998_244_353;
                }
            }
        }
        System.out.println(res);
    }
}