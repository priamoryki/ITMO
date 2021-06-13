public class ReverseAbc {
    public static void main(String[] args) {
        int numsInLine[] = new int[2_000_000]; //Assigment
        String nums[] = new String[2_000_000];
        int n = 0;

        Scanner scn = new Scanner(System.in); //Reading
        while (scn.hasNext()) {
            nums[n++] = scn.next();
            numsInLine[scn.numOfLines]++;
        }
        scn.close();

        for (int i = scn.numOfLines - 1; i >= 0; i--) { //Printing
            for (int j = 1; j <= numsInLine[i]; j++) {
                System.out.print(nums[n - (j + 1)] + " ");
            }
            System.out.println();
            n -= numsInLine[i];
        }
    }
}