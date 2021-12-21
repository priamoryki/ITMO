import java.io.*;

public class TaskD {
    public static int[] parent = new int[200_000];
    public static int logM = 0;

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

    public static int log2(int n) {
        if (n == 0) {
            return 0;
        }
        return log2(n / 2) + 1;
    }

    public static class Tree {
        int root = 1, current = 1, log;
        int[] parent, depth;
        int[][] dp;
        boolean[] isAlive;

        Tree(int size) {
            size += 10;
            isAlive = new boolean[size];
            this.parent = new int[size];
            this.depth = new int[size];
            this.dp = new int[size][log2(size) + 1];
            log = log2(size);
            isAlive[1] = true;
        }

        public void add(int p) {
            current++;
            depth[current] = depth[p] + 1;
            isAlive[current] = true;
            parent[current] = p;
            dp[current][0] = p;
            for (int i = 1; i <= log2(current); i++) {
                dp[current][i] = dp[dp[current][i - 1]][i - 1];
            }
        }

        public void kill(int v) {
            isAlive[v] = false;
        }

        private int findParent(int lca) {
            if (isAlive[lca]) {
                return lca;
            }
            parent[lca] = findParent(parent[lca]);
            return parent[lca];
        }

        public int lca(int u, int v) {
            if (depth[u] > depth[v]) {
                int temp = u;
                u = v;
                v = temp;
            }

            int h = depth[v] - depth[u];
            for (int i = log; i >= 0; i--) {
                if ((1 << i) <= h) {
                    h -= (1 << i);
                    v = dp[v][i];
                }
            }
            if (u == v) {
                return u;
            }
            for (int i = log; i >= 0; i--) {
                if (dp[u][i] != dp[v][i]) {
                    u = dp[u][i];
                    v = dp[v][i];
                }
            }
            return parent[u];
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int m = scn.nextInt();
        Tree tree = new Tree(m);
        for (int i = 0; i < m; i++) {
            String req = scn.next();
            int v = scn.nextInt();
            if (req.equals("+")) {
                tree.add(v);
            } else if (req.equals("-")) {
                tree.kill(v);
            } else if (req.equals("?")) {
                writer.println(tree.findParent(tree.lca(v, scn.nextInt())));
            }
        }
        writer.close();
        scn.close();
    }
}