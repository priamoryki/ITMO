import java.io.*;
import java.util.*;

public class TaskB {
    private static int n, m;
    private static ArrayList<Pair>[] out;

    public static class Scanner {
        private final BufferedReader reader;
        private final char[] buffer = new char[1024];
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
    }

    public static int[] solve() {
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        PriorityQueue<Pair> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.right));
        q.add(new Pair(0, 0));
        while (!q.isEmpty()) {
            Pair p = q.poll();
            int u = p.right;

            if (p.left > dp[u]) {
                continue;
            }

            for (Pair v : out[u]) {
                if (dp[u] + v.right < dp[v.left]) {
                    dp[v.left] = dp[u] + v.right;
                    q.add(new Pair(dp[v.left], v.left));
                }
            }
        }

        return dp;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        n = scn.nextInt();
        m = scn.nextInt();

        out = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = scn.nextInt() - 1;
            int v = scn.nextInt() - 1;
            int w = scn.nextInt();
            out[u].add(new Pair(v, w));
            out[v].add(new Pair(u, w));
        }

        for (int i : solve()) {
            writer.write(i + " ");
        }

        writer.close();
        scn.close();
    }
}