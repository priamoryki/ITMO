import java.util.Scanner;

public class TaskI {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int xl = Integer.MAX_VALUE;
        int xr = Integer.MIN_VALUE;
        int yl = Integer.MAX_VALUE;
        int yr = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int x = scn.nextInt();
            int y = scn.nextInt();
            int h = scn.nextInt();
            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }
        scn.close();
        int x = (xl + xr)/2;
        int y = (yl + yr)/2;
        int h = (Math.max(xr - xl, yr - yl) + 1)/2;
        System.out.println(x + " " + y + " " + h);
    }
}