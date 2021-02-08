import java.util.Scanner;

public class TaskE {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int m = scn.nextInt();
        int[] v = new int[n - 1];
        int[] u = new int[n - 1];
        int[] c = new int[n];
        for (int i = 0; i < n - 1; i++) {
            v[i] = scn.nextInt();
            u[i] = scn.nextInt();
        }
        for (int i = 0; i < m; i++) {
            c[i] = scn.nextInt();
        }
        scn.close();
    }
}