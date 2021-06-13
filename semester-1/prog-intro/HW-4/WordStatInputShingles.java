import java.io.*;
import java.util.*;

public class WordStatInputShingles {
    public static void main(String[] args) {
        int n = 0;
        String[] words = new String[1000000];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8")); //Reading
            char prev1 = '\'';
            char prev2 = '\'';
            try {
                while (true) {
                    char[] buffer = new char[1024];
                    StringBuilder s = new StringBuilder(prev2 + prev1);
                    int inp = reader.read(buffer);
                    if (inp < 0) {
                        break;
                    } else {
                        s.append(new String(buffer, 0, inp)); //Spliting into words
                        s.append(" ");
                        int st = 0;
                        for (int i = 0; i < s.length(); i++) {
                            if (!(Character.getType(s.charAt(i)) == Character.DASH_PUNCTUATION || s.charAt(i) == '\'' || Character.isLetter(s.charAt(i)))) {
                                if (i - st >= 3) {
                                    String word = s.substring(st, i).toLowerCase();
                                    for (int j = 3; j <= word.length(); j++) {
                                        words[n++] = word.substring(j - 3, j);
                                    }
                                }
                                st = i + 1;
                            }
                        }
                        prev1 = s.charAt(s.length() - 2);
                        prev2 = s.charAt(s.length() - 3);
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8")); //Printing
            try {
                for (int i = 0; i < n; i++) {
                    if (words[i].length() > 0) {
                        int amount = 0;
                        String word = words[i];
                        for (int j = i; j < n; j++) {
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