import java.io.*;
import java.util.ArrayList;

public class TaskE {
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
        int key, value;
 
        Pair(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static int log2(int n) {
        if (n == 0) {
            return 0;
        }
        return log2(n / 2) + 1;
    }

    static class Tree {
        int root = 1, size, log;
        int[] parent, depth;
        int[][] dp;
        ArrayList<Pair> maxJmp;
        ArrayList<ArrayList<Integer>> edges;

        Tree(int size) {
            maxJmp = new ArrayList<>();
            for (int i = 0; i <= size + 10; i++) {
                maxJmp.add(new Pair(i, -Integer.MAX_VALUE));
            }
            this.size = size;
            this.parent = new int[size + 10];
            this.depth = new int[size + 10];
            this.dp = new int[size + 10][log2(size) + 1];
            this.edges = new ArrayList<>();
            for (int i = 0; i <= size + 10; i++) {
                edges.add(new ArrayList<>());
            }
            log = log2(size);
        }

        public int countUselessEdges() {
            return size - 1 - countPaths(1, 0);
        }

        public void query(int v, int u) {
            int lca = lca(u, v);
            getMaxJump(v, lca);
            getMaxJump(u, lca);
        }

        public void getMaxJump(int v, int p) {
            maxJmp.set(v, (new Pair(v, Math.max(Math.abs(depth[v] - depth[p]), maxJmp.get(v).value))));
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

        public int countPaths(int v, int p) {
            int result = 0;
            for (Integer i : edges.get(v)) {
                if (i == p) {
                    continue;
                }
                result += countPaths(i, v);
                maxJmp.set(v, new Pair(v, Math.max(maxJmp.get(v).value, maxJmp.get(i).value - 1)));
            }
            if (maxJmp.get(v).value > 0) {
                return ++result;
            } else {
                return result;
            }
        }

        public void dfs(int v, int prev, int d) {
            depth[v] = d;
            for (int i = 0; i < edges.get(v).size(); i++) {
                int to = edges.get(v).get(i);
                if (to != prev) {
                    dfs(to, v, d + 1);
                }
            }
        }

        public void dfsP(int v, int prev) {
            for (int i = 0; i < edges.get(v).size(); i++) {
                int to = edges.get(v).get(i);
                if (to != prev) {
                    parent[to] = v;
                    dfsP(to, v);
                }
            }
        }

        public void setDp() {
            for (int i = 1; i <= size; i++) {
                dp[i][0] = parent[i];
            }
            for (int j = 1; j <= log; j++) {
                for (int i = 1; i <= size; i++) {
                    dp[i][j] = dp[dp[i][j - 1]][j - 1];
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int n = scn.nextInt();
        Tree tree = new Tree(n);
        for (int i = 0; i < n - 1; i++) {
            int x = scn.nextInt();
            int y = scn.nextInt();
            tree.edges.get(x).add(y);
            tree.edges.get(y).add(x);
        }
        tree.dfsP(1, 0);
        tree.setDp();
        tree.dfs(tree.root, 1,0);
        int m = scn.nextInt();
        for (int i = 0; i < m; i++) {
            tree.query(scn.nextInt(), scn.nextInt());
        }
        writer.println(tree.countUselessEdges());
        writer.close();
        scn.close();
    }
}