import java.util.Scanner;

public class TaskJ {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            String x = scn.next();
            for (int j = 0; j < n; j++) {
                a[i][j] = x.charAt(j) - '0';
            }
        }
        scn.close();

        for (int k = 0; k < n; k++) {
            for (int i = k + 1; i < n; i++) {
                if (a[k][i] == 1) {
                    for (int j = i + 1; j < n; j++) {
                        a[k][j] = (a[k][j] - a[i][j] + 10) % 10;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }
}