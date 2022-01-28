import java.io.*;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class TaskD {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n, m, timer, color;
    private static int[] timeIn = new int[1_000_000];
    private static int[] up = new int[100_000];
    private static ArrayList<Pair>[] out = new ArrayList[100_000];
    private static int[] components = new int[100_000];
    private static int[] colors = new int[100_000];
    private static ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

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

    private static void setComponent() {
        components[queue.removeFirst()] = color;
    }

    private static void dfs(int v, int u) {
        colors[v] = 1;
        timeIn[v] = up[v] = timer++;
        queue.addFirst(v);
        for (Pair a : out[v]) {
            if (a.right == u) {
                continue;
            }
            if (colors[a.left] == 1) {
                up[v] = Math.min(up[v], timeIn[a.left]);
            } else if (colors[a.left] == 0) {
                dfs(a.left, a.right);
                up[v] = Math.min(up[v], up[a.left]);
                if (up[a.left] > timeIn[v]) {
                    while (queue.getFirst() != a.left) {
                        setComponent();
                    }
                    setComponent();
                    color++;
                }
            }
        }
        colors[v] = 2;
    }

    private static void solve() {
        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                dfs(i, -1);
                while (!queue.isEmpty()) {
                    setComponent();
                }
                color++;
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i < n; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int from = scn.nextInt() - 1;
            int to = scn.nextInt() - 1;
            out[from].add(new Pair(to, i));
            out[to].add(new Pair(from, i));
        }

        solve();

        writer.write(color + "\n");
        for (int i = 0; i < n; i++) {
            writer.write((components[i] + 1) + " ");
        }

        writer.close();
        scn.close();
    }
}