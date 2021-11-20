import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Pavel Lymar
 */
public class Main {
    public static void main(String[] args) {
        int[] numsInLine = new int[1_000_000]; //Assigment
        int[] nums = new int[1_000_000];
        int n = 0, linesNum = 0;

        Scanner scn = new Scanner(System.in); //Reading
        while (scn.hasNext()) {
            Scanner numScn = new Scanner(new ByteArrayInputStream(scn.nextLine().getBytes(StandardCharsets.UTF_8)));
            int i = 0;
            while (numScn.hasNext()) {
                nums[n + i] = numScn.nextInt();
                i++;
            }
            numsInLine[linesNum++] = i;
            n += i;
        }
        scn.close();

        for (int i = linesNum - 1; i >= 0; i--) { //Printing
            for (int j = 1; j <= numsInLine[i]; j++) {
                System.out.print(nums[n - j] + " ");
            }
            System.out.println();
            n -= numsInLine[i];
        }
    }
}
