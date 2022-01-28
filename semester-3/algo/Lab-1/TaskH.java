import java.io.*;
import java.util.ArrayList;

public class TaskH {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n, color;
    private static boolean[] used = new boolean[1_010];
    private static ArrayList<Pair>[] out = new ArrayList[1_010];
    private static ArrayList<Pair>[] invertedOut = new ArrayList[1_010];

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

    private static boolean check(int n) {
        color = 0;
        used = new boolean[used.length];

        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                color++;
                dfs(i, -1, n, out);
            }
        }

        int startColor = color;
        color = 0;
        used = new boolean[used.length];

        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                color++;
                dfs(i, -1, n, invertedOut);
            }
        }

        return startColor == 1 && color == 1;
    }

    private static void dfs(int v, int u, int m, ArrayList<Pair>[] graph) {
        used[v] = (color > 0);

        for (Pair to : graph[v]) {
            if (!used[to.left] && to.left != u && to.right <= m) {
                dfs(to.left, v, m, graph);
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        used = new boolean[n];

        for (int i = 0; i < 1_010; i++) {
            out[i] = new ArrayList<>();
            invertedOut[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x = scn.nextInt();
                out[i].add(new Pair(j, x));
                invertedOut[j].add(new Pair(i, x));
            }
        }

        int left = -1, right = 1_000_000_000;
        while (right - left > 1) {
            int mid = (right + left) / 2;
            if (check(mid)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        writer.write(right + "\n");
        writer.close();
        scn.close();
    }
}