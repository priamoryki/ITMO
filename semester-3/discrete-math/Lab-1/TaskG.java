import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * @author Pavel Lymar
 */
public class TaskG {
    private static final Scanner scn = new Scanner(System.in);
    private static final int MAXN = 10_010;
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static final BitSet[] possibleColors = new BitSet[MAXN];
    private static final ArrayList<Integer>[] out = new ArrayList[MAXN];
    private static final int[] color = new int[MAXN];
    private static final boolean[] used = new boolean[MAXN];
    private static int n, m, result;

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

    private static void dfs(int u) {
        used[u] = true;
        color[u] = possibleColors[u].nextSetBit(0);
        for (int i : out[u]) {
            possibleColors[i].set(color[u], false);
        }
        for (int i : out[u]) {
            if (!used[i]) {
                dfs(i);
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i < MAXN; i++) {
            out[i] = new ArrayList<>();
            possibleColors[i] = new BitSet(MAXN);
            possibleColors[i].set(0, MAXN, true);
        }

        for (int i = 0; i < m; i++) {
            int u = scn.nextInt();
            int v = scn.nextInt();
            out[u].add(v);
            out[v].add(u);
            result = Math.max(result, Math.max(out[u].size(), out[v].size()));
        }

        dfs(1);

        writer.write(result + (result + 1) % 2 + "\n");
        for (int i = 1; i <= n; i++) {
            writer.write(color[i] + 1 + "\n");
        }

        scn.close();
        writer.close();
    }
}
