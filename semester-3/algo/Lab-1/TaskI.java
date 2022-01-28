import java.io.*;

public class TaskI {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n;
    private static double weight;
    private static int[] e = new int[10_005];
    private static double[] medge = new double[10_005];
    private static boolean[] used = new boolean[10_005];
    private static Pair[] points = new Pair[10_005];

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
        private int x, y;

        Pair(int u, int v) {
            this.x = u;
            this.y = v;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public boolean equals(Object other) {
            Pair oth = null;
            if (other instanceof Pair) {
                oth = (Pair) other;
            }
            return oth != null && this.x == oth.x && this.y == oth.y;
        }
    }

    private static double distance(Pair e1, Pair e2) {
        return Math.sqrt((e1.x - e2.x) * (e1.x - e2.x) + (e1.y - e2.y) * (e1.y - e2.y));
    }

    private static void solve() {
        for (int i = 0; i < n; i++) {
            medge[i] = Double.MAX_VALUE;
            e[i] = -1;
        }

        medge[0] = 0;

        for (int i = 0 ; i < n; i++) {
            int u = -1;

            for (int j = 0; j < n; j++) {
                if (!used[j] && (u == -1 || medge[j] < medge[u])) {
                    u = j;
                }
            }

            used[u] = true;

            if (e[u] != -1) {
                weight += distance(points[u], points[e[u]]);
            }

            for (int j = 0; j < n; j++) {
                if (distance(points[u], points[j]) < medge[j]) {
                    medge[j] = distance(points[u], points[j]);
                    e[j] = u;
                }
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();

        for (int i = 0; i < n; i++) {
            int x1 = scn.nextInt();
            int y1 = scn.nextInt();
            points[i] = new Pair(x1, y1);
        }

        solve();

        writer.write(weight + "\n");
        writer.close();
        scn.close();
    }
}