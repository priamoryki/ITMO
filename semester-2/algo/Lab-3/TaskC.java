import java.io.*;

public class TaskC {
    public static int[] d = new int[200_000];
    public static int[][] dp = new int[200_000][20];
    public static int[][] minOnWay = new int[200_000][20];
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

    public static int getMin(int u, int v) {
        int result = Integer.MAX_VALUE;
        if (d[v] > d[u]) {
            int temp = u;
            u = v;
            v = temp;
        }
        for (int i = logN; i >= 0; i--) {
            if (d[u] - (1 << i) >= d[v]) {
                result = Math.min(result, minOnWay[u][i]);
                u = dp[u][i];
            }
        }
        if (u == v) {
            return result;
        }
        for (int i = logN; i >= 0; i--) {
            if (dp[v][i] != dp[u][i]) {
                result = Math.min(Math.min(minOnWay[v][i], minOnWay[u][i]), result);
                v = dp[v][i];
                u = dp[u][i];
            }
        }
        return Math.min(Math.min(minOnWay[v][0], minOnWay[u][0]), result);
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int n = scn.nextInt();
        logN = (int) Math.ceil(Math.log(n) / Math.log(2));
        minOnWay[0][0] = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            int x = scn.nextInt();
            dp[i][0] = x - 1;
            d[i] = d[x - 1] + 1;
            minOnWay[i][0] = scn.nextInt();
        }
        for (int j = 1; j <= logN; j++) {
            for (int i = 0; i < n; i++) {
                dp[i][j] = dp[dp[i][j - 1]][j - 1];
                minOnWay[i][j] = Math.min(minOnWay[i][j - 1], minOnWay[dp[i][j - 1]][j - 1]);
            }
        }
        int m = scn.nextInt();
        for (int i = 0; i < m; i++) {
            writer.write(Integer.toString(getMin(scn.nextInt() - 1, scn.nextInt() - 1)) + "\n");
        }
        writer.close();
        scn.close();
    }
}