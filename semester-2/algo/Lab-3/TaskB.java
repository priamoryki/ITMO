import java.io.*;

public class TaskB {
    public static int[] lastDp = new int[200_000];
    public static int[] d = new int[200_000];
    public static int[][] dp = new int[200_000][50];
    public static int logN = 0;

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

    public static int lca(int u, int v) {
        if (d[v] > d[u]) {
            int temp = u;
            u = v;
            v = temp;
        } 
        for (int i = logN; i >= 0; i--) {
            if (d[u] - (1 << i) >= d[v]) {
                u = dp[u][i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = logN; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return dp[v][0];
    }

    public static void appendInDp(int index, int value) {
        dp[index][lastDp[index]++] = value;
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int n = scn.nextInt();
        for (int i = 1; i < n; i++) {
            int x = scn.nextInt();
            appendInDp(i, x - 1);
            d[i] = d[x - 1] + 1;
        }
        for (int j = 1; 1 << (j - 1) < n; j++) {
            for (int i = 0; i < n; i++) {
                appendInDp(i, dp[dp[i][j - 1]][j - 1]);
            }
            logN++;
        }
        int m = scn.nextInt();
        for (int i = 0; i < m; i++) {
            writer.write(Integer.toString(lca(scn.nextInt() - 1, scn.nextInt() - 1) + 1) + "\n");
        }
        writer.close();
        scn.close();
    }
}