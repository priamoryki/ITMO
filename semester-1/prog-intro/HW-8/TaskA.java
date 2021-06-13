import java.util.Scanner;

public class TaskA {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int a = scn.nextInt();
        int b = scn.nextInt();
        int n = scn.nextInt();
        scn.close();
        System.out.println((int)(2*Math.ceil((double)(n - b)/(b - a)) + 1));
    }
}