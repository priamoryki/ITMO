import java.io.*;
import java.util.*;

public class WordStatInput {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8")); //Reading
            try {
                char[] buffer = new char[1024];
                while (true) {
                    int inp = reader.read(buffer);
                    if (inp < 0) {
                        break;
                    }
                    s.append(new String(buffer, 0, inp));
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        s.append(" "); //Spliting into words
        String[] words = new String[1000000];
        int n = 0;
        int st = 0;
        for (int i = 0; i < s.length(); i++) {
            if (!(Character.getType(s.charAt(i)) == Character.DASH_PUNCTUATION || s.charAt(i) == (char) 39 || Character.isLetter(s.charAt(i)))) {
                if (i > st) {
                    words[n++] = s.substring(st, i).toLowerCase();
                }
                st = i + 1;
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8")); //Printing
            try {
                for (int i = 0; i < n; i++) {
                    if (words[i] != "") {
                        int amount = 0;
                        String word = words[i];
                        for (int j = 0; j < n; j++) {
                            if (word.equals(words[j])) {
                                amount++;
                                words[j] = "";
                            }
                        }
                        writer.write(word + " " + amount + "\n");
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