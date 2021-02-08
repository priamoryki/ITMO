import java.util.Arrays;
import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        int numsinline[] = new int[1000000]; //Assigment
        int nums[] = new int[1000000];
        int linesnum = 0;
        int n = 0;

        Scanner scn = new Scanner(System.in); //Reading
        while (scn.hasNextLine()) {
            Scanner numscn = new Scanner(scn.nextLine());
            int i = 0;
            while (numscn.hasNextInt()) {
                nums[n + i] = numscn.nextInt();
                i++;
            }
            numsinline[linesnum] = i;
            n += i;
            linesnum++;
        }
        scn.close();

        for (int i = linesnum - 1; i >= 0; i--) { //Printing
            for (int j = 1; j <= numsinline[i]; j++) {
                System.out.print(nums[n - j] + " ");
            }
            System.out.println();
            n -= numsinline[i];
        }
    }
}