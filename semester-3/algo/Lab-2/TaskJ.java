import java.io.*;
import java.util.*;

public class TaskJ {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static int n, m;
    private static boolean[] used;
    private static int[] result;
    private static ArrayList<Integer>[] out;
    private static final ArrayList<Integer> top = new ArrayList<>();

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

    private static void topsort(int u) {
        used[u] = true;
        for (int v : out[u]) {
            if (!used[v]) {
                topsort(v);
            }
        }
        top.add(u);
    }

    private static void setGrandi(int v) {
        boolean[] mex = new boolean[n];
        for (int i : out[v]) {
            mex[result[i]] = true;
        }
        while (mex[result[v]]) {
            result[v]++;
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        used = new boolean[n];
        result = new int[n];
        out = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = scn.nextInt() - 1;
            int v = scn.nextInt() - 1;
            out[u].add(v);
        }

        for (int i = 0; i < n; i++) {
            topsort(i);
        }
        for (int u : top) {
            setGrandi(u);
        }
        for (int i = 0; i < n; i++) {
            writer.write(result[i] + "\n");
        }

        writer.close();
        scn.close();
    }
}