import java.util.*;

public class TaskM {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int t = scn.nextInt();
        for (int l = 0; l < t; l++) {
            int n = scn.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = scn.nextInt();
            }
            Map<Integer, Integer> C = new HashMap<>();
            int res = 0;
            for (int j = n; j > 0; j--) {
                for (int i = 0; i < j - 1; i++) {
                    if (C.containsKey(2*a[j - 1] - a[i])) {
                        res += C.get(2*a[j - 1] - a[i]);
                    }
                }
                if (C.containsKey(a[j - 1])) {
                    C.replace(a[j - 1], C.get(a[j - 1]) + 1);
                } else {
                    C.put(a[j - 1], 1);
                }
            }
            System.out.println(res);
        }
        scn.close();
    }
}