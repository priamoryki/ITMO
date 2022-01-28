import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class TaskF {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n, m, color = 1;
    private static int[] comp = new int[100_000];
    private static boolean[] used = new boolean[100_000];
    private static ArrayList<Integer>[] out = new ArrayList[100_000];
    private static ArrayList<Integer>[] invertedOut = new ArrayList[100_000];
    private static ArrayList<Integer> a = new ArrayList<>();
    private static HashSet<Pair> isConnected = new HashSet<>();

    public static class Scanner {
        private BufferedReader reader;
        private char[] buffer = new char[1024];
        private int id = 0;
        private int inp = 0;
        public int numOfLines = 0;

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

        private void read() {
            try {
                inp = reader.read(buffer);
                id = 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public boolean hasNext() {
            while (id >= inp && inp != -1) {
                read();
            }
            return (inp > id);
        }

        public String next() {
            hasNext();
            StringBuilder next = new StringBuilder();
            while (id < inp) {
                if (!Character.isWhitespace(buffer[id])) {
                    next.append(buffer[id++]);
                    if (id >= inp) {
                        read();
                    }
                } else if (next.length() != 0) {
                    break;
                } else {
                    if (buffer[id] == '\n') {
                        numOfLines++;
                    }
                    id++;
                    if (id >= inp && !hasNext()) {
                        break;
                    }
                }
            }
            return next.toString();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static class Pair {
        public int left, right;

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

    private static void dfs1(int v) {
        used[v] = true;
        for (int to : out[v]) {
            if (!used[to]) {
                dfs1(to);
            }
        }
        a.add(v);
    }

    private static void dfs2(int v) {
        comp[v] = color;
        for (Integer to : invertedOut[v]) {
            if (comp[to] == 0) {
                dfs2(to);
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i < n; i++) {
            out[i] = new ArrayList<>();
            invertedOut[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = scn.nextInt() - 1;
            int to = scn.nextInt() - 1;
            out[from].add(to);
            invertedOut[to].add(from);
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs1(i);
            }
        }

        for (int i = a.size() - 1; i >= 0; i--) {
            if (comp[a.get(i)] == 0) {
                dfs2(a.get(i));
                color++;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < out[i].size(); j++) {
                Pair pair = new Pair(Math.min(comp[i], comp[out[i].get(j)]), Math.max(comp[i], comp[out[i].get(j)]));
                if (comp[i] != comp[out[i].get(j)] && !isConnected.contains(pair)) {
                    isConnected.add(pair);
                }
            }
        }

        writer.write(isConnected.size() + "\n");
        writer.close();
        scn.close();
    }
}