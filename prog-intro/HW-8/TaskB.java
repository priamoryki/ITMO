import java.util.Scanner;

public class TaskB {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        scn.close();
        for (int i = 0; i < n; i++) {
            System.out.println(-710*(25_000 - i));
        }
    }
}