import java.io.*;
import java.util.*;

public class WordStatSortedLineIndex {
    public static void main(String[] args) {
        int n = 0;
        int maxLine = 0;
        int i = 0;
        TreeMap<String, IntList> counter = new TreeMap<>();
        TreeMap<String, IntList> line = new TreeMap<>();
        Scanner scn = new Scanner(new File(args[0]));
        while (scn.hasNext()) {
            i++;
            String word = scn.next().toLowerCase();
            if (maxLine < scn.numOfLines) {
                maxLine = scn.numOfLines;
                n = i - 1;
            }
            if (counter.containsKey(word)) {
                counter.get(word).append(i - n);
                line.get(word).append(scn.numOfLines + 1);
            } else {
                counter.put(word, new IntList(i - n));
                line.put(word, new IntList(scn.numOfLines + 1));
            }
        }
        scn.close();

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8")); //Printing
            try {
                for (String word : counter.keySet()) {
                    if (word.length() > 0) {
                        IntList a = counter.get(word);
                        IntList b = line.get(word);
                        writer.write(word + " " + a.length + " ");
                        for (i = 0; i < a.length; i++) {
                            writer.write(b.get(i) + ":" + a.get(i));
                            if (i != a.length - 1) {
                                writer.write(" ");
                            }
                        }
                        writer.write('\n');
                    }
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}