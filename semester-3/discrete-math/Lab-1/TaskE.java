import java.io.*;
import java.util.ArrayList;

/**
 * @author Pavel Lymar
 */
public class TaskE {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static final ArrayList<Integer>[] out = new ArrayList[100_010];
    private static final int[] parent = new int[100_010];
    private static int n;

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

    private static void dfs(int v) {
        for (int i : out[v]) {
            if (i != parent[v]) {
                parent[i] = v;
                dfs(i);
            }
        }
    }

    private static ArrayList<Integer> prufersCode() {
        ArrayList<Integer> result = new ArrayList<>();
        int[] power = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            power[i] = out[i].size();
        }
        parent[n] = -1;
        dfs(n);
        int p = 1;
        while (p < n && power[p] != 1) {
            p += 1;
        }
        int node = p;
        for (int i = 0; i < n - 2; i++) {
            int next = parent[node];
            if (next != -1) {
                result.add(next);
            }
            power[next]--;
            if (power[next] == 1 && next < p) {
                node = next;
            } else {
                p++;
                while (p <= n && power[p] != 1) {
                    p++;
                }
                node = p;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        n = scn.nextInt();

        for (int i = 0; i < n + 1; i++) {
            out[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            int u = scn.nextInt();
            int v = scn.nextInt();
            out[u].add(v);
            out[v].add(u);
        }

        for (int i : prufersCode()) {
            writer.write(i + " ");
        }

        scn.close();
        writer.close();
    }
}
