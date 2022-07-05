import java.io.*;
import java.util.*;

/**
 * @author Pavel Lymar
 */
public class TaskE {
    private static final int SIZE = 1000;
    private static ArrayList<ArrayList<Integer>> g;
    private static final ArrayList<Edge> edges = new ArrayList<>();
    private static int[] p, d, from;
    private static int result;

    private static class Edge {
        public int u, v, c, f = 0, w, pathNum;

        public Edge(int u, int v, int c, int w, int pathNum) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.w = w;
            this.pathNum = pathNum;
        }
    }

    private static class Pair implements Comparable<Pair> {
        public final int left;
        public final int right;

        public Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Pair o) {
            int result = Integer.compare(left, o.left);
            if (result == 0) {
                result = Integer.compare(right, o.right);
            }
            return result;
        }
    }

    private static class Scanner implements AutoCloseable {
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

        public int nextInt() {
            try {
                return Integer.parseInt(next());
            } catch (NumberFormatException e) {
                return Integer.MIN_VALUE;
            }
        }
    }

    public static void dijkstra() {
        d = new int[SIZE];
        Arrays.fill(d, Integer.MAX_VALUE);
        d[1] = 0;
        from = new int[SIZE];
        Arrays.fill(from, -1);
        PriorityQueue<Pair> A = new PriorityQueue<>();
        A.add(new Pair(0, 1));
        while (!A.isEmpty()) {
            Pair pair = A.peek();
            int u = pair.right;
            A.remove();
            for (int id : g.get(u)) {
                Edge e = edges.get(id);
                int wh = e.w + p[u] - p[e.v];
                if (e.f < e.c && d[u] + wh < d[e.v]) {
                    A.remove(new Pair(d[e.v], e.v));
                    d[e.v] = d[u] + wh;
                    from[e.v] = id;
                    A.add(new Pair(d[e.v], e.v));
                }
            }
        }
    }

    private static int dfs(int id, int flow) {
        if (id == -1) {
            return flow;
        }
        Edge uv = edges.get(id);
        result += uv.w;
        int delta = dfs(from[uv.u], Math.min(flow, uv.c - uv.f));
        uv.f += delta;
        edges.get(id ^ 1).f -= delta;
        return delta;
    }

    private static void dfs1(int u, int t, ArrayList<Integer> path) {
        if (u == t) {
            return;
        }
        for (int v : g.get(u)) {
            if (edges.get(v).f == 1) {
                edges.get(v).f = 0;
                path.add(edges.get(v).pathNum);
                dfs1(edges.get(v).v, t, path);
                return;
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scn = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int n = scn.nextInt();
            int m = scn.nextInt();
            int k = scn.nextInt();
            g = new ArrayList<>();
            for (int i = 0; i < SIZE; i++) {
                g.add(new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                int u = scn.nextInt();
                int v = scn.nextInt();
                int w = scn.nextInt();
                g.get(u).add(edges.size());
                edges.add(new Edge(u, v, 1, w, i + 1));
                g.get(v).add(edges.size());
                edges.add(new Edge(v, u, 0, -w, i + 1));
                g.get(u).add(edges.size());
                edges.add(new Edge(u, v, 0, -w, i + 1));
                g.get(v).add(edges.size());
                edges.add(new Edge(v, u, 1, w, i + 1));
            }

            p = new int[SIZE];
            dijkstra();
            p = d;
            boolean flag = false;
            for (int i = 0; i < k; i++) {
                if (d[n] != Integer.MAX_VALUE) {
                    dfs(from[n], 1);
                    dijkstra();
                    for (int j = 0; j < SIZE; j++) {
                        p[j] += d[j];
                    }
                } else {
                    writer.write("-1\n");
                    // for some reason you can't write "System.exit(0);", so i need flag
                    flag = true;
                }
            }
            if (!flag) {
                writer.write((double) result / k + "\n");
                for (int i = 0; i < k; i++) {
                    ArrayList<Integer> path = new ArrayList<>();
                    dfs1(1, n, path);
                    writer.write(path.size() + " ");
                    for (int j : path) {
                        writer.write(j + " ");
                    }
                    writer.write("\n");
                }
            }
        } catch (Exception ignored) {

        }
    }
}