import java.util.Arrays;
import java.util.Scanner;

public class ReverseMin {
    public static void main(String[] args) {
        int numsInLine[] = new int[1000000]; //Assigment
        int nums[] = new int[1000000];
        int minInLine[] = new int[1000000];
        int minInCol[] = new int[1000000];
        int linesNum = 0;
        int n = 0;
        int maxId = -1;

        Scanner scn = new Scanner(System.in); //Reading
        while (scn.hasNextLine()) {
            Scanner numscn = new Scanner(scn.nextLine());
            int i = 0;
            minInLine[linesNum] = Integer.MAX_VALUE;
            while (numscn.hasNextInt()) {
                nums[n + i] = numscn.nextInt();
                if (i > maxId) {
                    minInCol[i] = nums[n + i];
                    maxId = i;
                }
                minInLine[linesNum] = Math.min(minInLine[linesNum], nums[n + i]);
                minInCol[i] = Math.min(minInCol[i], nums[n + i]);
                i++;
            }
            numsInLine[linesNum] = i;
            n += i;
            linesNum++;
        }
        scn.close();

        int t = 0;
        for (int i = 0; i < linesNum; i++) { //Printing
            for (int j = 0; j < numsInLine[i]; j++) {
                int printingNum = nums[t + j];
                printingNum = Math.min(printingNum, Math.min(minInLine[i], minInCol[j]));
                System.out.print(printingNum + " ");
            }
            System.out.println();
            t += numsInLine[i];
        }
    }
}