import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Pavel Lymar
 */
public class TaskK {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static int n, r;
    private static boolean[] used;
    private static int[] grandi;
    private static ArrayList<Integer>[] out;
    private static final HashMap<Pair, Integer> edges = new HashMap<>();

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

    public static class Pair {
        public int left;
        public int right;

        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int hashCode() {
            return 31 * left + right;
        }

        @Override
        public boolean equals(Object other) {
            Pair oth = null;
            if (other instanceof Pair) {
                oth = (Pair) other;
            }
            return oth != null && this.left == oth.left && this.right == oth.right;
        }
    }

    public static void dfs(int v) {
        used[v] = true;
        for (int u : out[v]) {
            if (!used[u]) {
                dfs(u);
                grandi[v] ^= (grandi[u] + 1);
            }
        }
    }

    public static Pair find(int i, int s) {
        used[i] = true;
        for (int j : out[i]) {
            if (!used[j]) {
                Pair res = ((grandi[j] + 1) ^ grandi[i] ^ s) == 0 ?
                                new Pair(i, j) : find(j, ((grandi[j] + 1) ^ grandi[i] ^ s) - 1);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        r = scn.nextInt();

        used = new boolean[n + 1];
        grandi = new int[n + 1];
        out = new ArrayList[n + 1];
        for (int i = 0; i < n + 1; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            int u = scn.nextInt();
            int v = scn.nextInt();
            out[u].add(v);
            out[v].add(u);
            edges.put(new Pair(Math.min(u, v), Math.max(u, v)), i);
        }

        dfs(r);
        Arrays.fill(used, false);

        if (grandi[r] != 0) {
            Pair res = find(r, 0);
            writer.write("1\n" + edges.get(new Pair(Math.min(res.left, res.right), Math.max(res.left, res.right))));
        } else {
            writer.write("2");
        }

        scn.close();
        writer.close();
    }
}
