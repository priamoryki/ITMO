import java.io.*;
import java.util.*;

public class WordStatInputPrefix {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8")); //Reading
        StringBuilder s = new StringBuilder();
        char[] buffer = new char[1024];
        while (true) {
            int inp = reader.read(buffer);
            if (inp < 0) {
                break;
            }
            s.append(new String(buffer, 0, inp));
        }
        reader.close();

        s.append(" ");
        String[] words = new String[1000000]; //Spliting input into words
        int n = 0;
        int st = 0;
        for (int i = 0; i < s.length(); i++) {
            if (!(Character.getType(s.charAt(i)) == Character.DASH_PUNCTUATION || s.charAt(i) == (char) 39 || Character.isLetter(s.charAt(i)))) {
                if (i - st >= 3) {
                    words[n++] = s.substring(st, st + 3).toLowerCase();
                }
                st = i + 1;
            }
        }

        HashMap<String, Integer> counter = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (counter.containsKey(words[i])) {
                counter.replace(words[i], counter.get(words[i]) + 1);
                words[i] = "";
            } else {
                counter.put(words[i], 1);
            }
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8")); //Printing
        for (int i = 0; i < n; i++) {
            if (words[i] != "") {
                writer.write(words[i] + " " + counter.get(words[i]) + "\n");
            }
        }
        writer.close();
    }
}