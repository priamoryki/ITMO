import java.io.*;
import java.util.ArrayList;

public class TaskJ {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n, m;
    private static long weight;
    private static int[] p = new int[210_000];
    private static int[] r = new int[210_000];
    private static ArrayList<Edge> edges = new ArrayList<>();

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

    public static class Edge implements Comparable<Edge> {
        private int u, v, w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.w, o.w);
        }

        @Override
        public int hashCode() {
            return 31 * 31 * u + 31 * v + w;
        }

        @Override
        public boolean equals(Object other) {
            Edge oth = null;
            if (other instanceof Edge) {
                oth = (Edge) other;
            }
            return oth != null && this.u == oth.u && this.v == oth.v && this.w == oth.w;
        }
    }

    private static int dsuGet(int u) {
        if (p[u] != u) {
            p[u] = dsuGet(p[u]);
        }
        return p[u];
    }

    private static void dsuUnion(int u, int v) {
        u = dsuGet(u);
        v = dsuGet(v);

        if (u != v) {
            if (r[u] < r[v]) {
                int temp = r[u];
                r[u] = r[v];
                r[v] = temp;
            }
            p[v] = u;
            r[u] += r[v];
        }
    }

    private static boolean dsuCheck(int u, int v) {
        return dsuGet(u) == dsuGet(v);
    }

    private static void solve() {
        for (int i = 0; i < n; i++) {
            p[i] = i;
            r[i] = 1;
        }

        edges.sort(Edge::compareTo);

        for (Edge e : edges) {
            if (!dsuCheck(e.u, e.v)) {
                dsuUnion(e.u, e.v);
                weight += e.w;
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i < m; i++) {
            int u = scn.nextInt() - 1;
            int v = scn.nextInt() - 1;
            int w = scn.nextInt();
            edges.add(new Edge(u, v, w));
        }

        solve();

        writer.write(weight + "\n");
        writer.close();
        scn.close();
    }
}