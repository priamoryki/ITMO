import java.io.*;
import java.util.*;

public class WordStatIndex {
    public static void main(String[] args) {
        int n = 0;
        int i = 0;
        String[] words = new String[1000000];
        HashMap<String, IntList> counter = new HashMap<>();
        Scanner scn = new Scanner(new File(args[0]));
        while (scn.hasNext()) {
            i++;
            String word = scn.next().toLowerCase();
            if (counter.containsKey(word)) {
                counter.get(word).append(i);
            } else {
                words[n++] = word;
                counter.put(word, new IntList(i));
            }
        }
        scn.close();

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8")); //Printing
            try {
                for (i = 0; i < n - 1; i++) {
                    writer.write(words[i] + " " + counter.get(words[i]) + "\n");
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}