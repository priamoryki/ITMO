import java.io.*;
import java.util.*;

public class TaskD {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);

    public static class Scanner {
        private final BufferedReader reader;
        private final char[] buffer = new char[1024];
        private int id = 0;
        private int bufferSize = 0;

        public Scanner(InputStream stream) {
            this.reader = new BufferedReader(new InputStreamReader(stream));
        }

        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        private void updateBuffer() {
            try {
                bufferSize = reader.read(buffer);
                id = 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public boolean hasNext() {
            if (id >= bufferSize) {
                updateBuffer();
            }
            return bufferSize != -1;
        }

        public Character nextChar() {
            if (hasNext()) {
                return buffer[id++];
            }
            return null;
        }

        public String next() {
            StringBuilder result = new StringBuilder();
            while (hasNext()) {
                Character c = nextChar();
                if (!Character.isWhitespace(c)) {
                    result.append(c);
                } else if (result.length() != 0) {
                    break;
                }
            }
            return result.toString();
        }

        public String nextLine() {
            StringBuilder result = new StringBuilder();
            while (hasNext()) {
                Character c = nextChar();
                if (c.equals('\n')) {
                    break;
                }
                result.append(c);
            }
            return result.toString();
        }

        public Integer nextInt() {
            try {
                return Integer.parseInt(next());
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private static int[] zFunction(String s) {
        int[] res = new int[s.length()];
        int left = 0;
        int right = 0;
        for (int i = 1; i < s.length(); i++) {
            res[i] = Math.max(0, Math.min(right - i, res[i - left]));
            while (i + res[i] < s.length() && s.charAt(res[i]) == s.charAt(i + res[i])){
                res[i] += 1;
            }
            if (i + res[i] > right) {
                left = i;
                right = i + res[i];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String p = scn.next();
        String t = scn.next();
        int[] z = zFunction(p + "#" + t);

        ArrayList<String> res = new ArrayList<>();

        for (int i = 0; i < t.length(); i++) {
            if (z[p.length() + 1 + i] == p.length()) {
                res.add(Integer.toString(i + 1));
            }
        }

        writer.write(res.size() + "\n");
        writer.write(String.join(" ", res));

        writer.close();
        scn.close();
    }
}