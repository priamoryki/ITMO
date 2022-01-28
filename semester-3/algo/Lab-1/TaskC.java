import java.io.*;
import java.util.ArrayList;

public class TaskC {
    private static int n, m, timer;
    private static boolean[] used = new boolean[100_000];
    private static int[] timeIn = new int[1_000_000];
    private static int[] up = new int[100_000];
    private static ArrayList<Integer>[] out = new ArrayList[100_000];
    private static boolean[] result = new boolean[100_000];

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

    private static void dfs(int v, int u) {
        used[v] = true;
        timeIn[v] = up[v] = timer++;
        int children = 0;
        for (int to : out[v]) {
            if (to == u) {
                continue;
            }
            if (used[to]) {
                up[v] = Math.min(up[v], timeIn[to]);
            } else {
                dfs(to, v);
                up[v] = Math.min(up[v], up[to]);
                if (up[to] >= timeIn[v] && u != -1) {
                    result[v] = true;
                }
                children++;
            }
        }
        if (u == -1 && children > 1) {
            result[v] = true;
        }
    }

    public static void findBridges() {
        for (int i = 1; i <= n; i++) {
            if (!used[i]) {
                dfs(i, -1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i <= n; i++) {
            out[i] = new ArrayList<>();
        }
    
        for (int i = 0; i < m; i++) {
            int from = scn.nextInt();
            int to = scn.nextInt();
            out[from].add(to);
            out[to].add(from);
        }
    
        findBridges();

        int counter = 0;
        for (int i = 0; i <= n; i++) {
            if (result[i]) {
                counter++;
            }
        }
        writer.write(counter + "\n");
        for (int i = 0; i <= n; i++) {
            if (result[i]) {
                writer.write(i + " ");
            }
        }
        
        writer.close();
        scn.close();
    }
}