import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskE {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n, m, timer, color;
    private static boolean[] used = new boolean[100_000];
    private static int[] timeIn = new int[100_000];
    private static int[] up = new int[100_000];
    private static ArrayList<Pair>[] out = new ArrayList[100_000];
    private static int[] colors = new int[200_010];

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
            return 20_000 * left + 20_000 * right;
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

    private static void paint(int u, int v, int cl) {
        used[u] = true;
        for (Pair p : out[u]) {
            int to = p.left;
            int ind = p.right;
            if (ind == v) {
                continue;
            }
            if (!used[to]) {
                int clr = cl;
                if (up[to] >= timeIn[u]) {
                    clr = ++color;
                }
                colors[ind] = clr;
                paint(to, ind, clr);
            } else if (timeIn[to] < timeIn[u]) {
                colors[ind] = cl;
            }
        }
    }

    private static void dfs(int u, int v) {
        used[u] = true;
        timeIn[u] = up[u] = timer++;
        for (Pair p : out[u]) {
            int to = p.left;
            int ind = p.right;
            if (ind == v) {
                continue;
            }
            if (used[to]) {
                up[u] = Math.min(up[u], timeIn[to]);
            } else {
                dfs(to, ind);
                up[u] = Math.min(up[u], up[to]);
            }
        }
    }

    private static void solve() {
        for (int u = 1; u <= n; u++) {
            if (!used[u]) {
                dfs(u, -1);
            }
        }
        used = new boolean[100_000];
        for (int u = 1; u <= n; ++u) {
            if (!used[u]) {
                paint(u, -1, ++color);
            }
        }
        color = 0;
        HashMap<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int u = colors[i];
            if (mp.get(u) == null) {
                mp.put(u, ++color);
            }
        }
        writer.write(color + "\n");
        for (int i = 0; i < m; i++) {
            int u = colors[i];
            writer.write(mp.get(u) + " ");
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i <= n; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = scn.nextInt();
            int to = scn.nextInt();
            out[from].add(new Pair(to, i));
            out[to].add(new Pair(from, i));
        }

        solve();

        writer.close();
        scn.close();
    }
}