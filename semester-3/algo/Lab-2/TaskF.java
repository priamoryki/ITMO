import java.io.*;
import java.util.*;

/**
 * @author Pavel Lymar
 *
 * Тест 36 оказался неподсильным для меня по времени, и я переписал на CPP
 */
public class TaskF {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static int n, m;
    private static final long INF = 10_000_000_000_000_000L;
    private static ArrayList<Pair>[] out;

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
        public long left;
        public int right;

        Pair(long left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int hashCode() {
            return (int) (31 * left + right);
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

    public static long[] dijkstra(int start) {
        long[] dp = new long[n];
        Arrays.fill(dp, INF);
        dp[start] = 0;

        TreeSet<Pair> q = new TreeSet<>(Comparator.comparingLong(a -> a.right));
        q.add(new Pair(0, start));
        while (!q.isEmpty()) {
            int u = q.pollFirst().right;
            for (Pair to : out[u]) {
                int v = (int) to.left;
                if (dp[u] + to.right < dp[v]) {
                    q.remove(new Pair(dp[v], v));
                    dp[v] = dp[u] + to.right;
                    q.add(new Pair(dp[v], v));
                }
            }
        }

        return dp;
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        out = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            out[i] =  new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = scn.nextInt() - 1;
            int v = scn.nextInt() - 1;
            int w = scn.nextInt();
            out[u].add(new Pair(v, w));
            out[v].add(new Pair(u, w));
        }

        int a = scn.nextInt() - 1;
        int b = scn.nextInt() - 1;
        int c = scn.nextInt() - 1;

        long[] distancesFromA = dijkstra(a);
        long[] distancesFromB = dijkstra(b);

        long res = Math.min(distancesFromA[b] + distancesFromB[c],
                        Math.min(distancesFromB[a] + distancesFromA[c],
                                distancesFromA[c] + distancesFromB[c]));

        writer.write((res < INF ? res : -1) + "\n");

        writer.close();
        scn.close();
    }
}
