import java.io.*;
import java.util.*;

public class TaskI {
    private static int n, m;
    private static ArrayList<Integer>[] out, reversedOut;

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

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);

        while (true) {
            try {
                n = scn.nextInt();
            } catch (NumberFormatException e) {
                break;
            }
            m = scn.nextInt();
            out = new ArrayList[n];
            reversedOut = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                out[i] = new ArrayList<>();
                reversedOut[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                int u = scn.nextInt() - 1;
                int v = scn.nextInt() - 1;
                out[u].add(v);
                reversedOut[v].add(u);
            }

            ArrayDeque<Integer> q = new ArrayDeque<>();
            int[] res = new int[n];
            int[] cnt = new int[n];
            for (int v = 0; v < n; v++) {
                if (out[v].isEmpty()) {
                    q.add(v);
                    res[v] = -1;
                }
            }

            while (!q.isEmpty()) {
                int v = q.pollFirst();
                if (res[v] == -1) {
                    for (int u : reversedOut[v]) {
                        if (res[u] == 0) {
                            res[u] = 1;
                            q.add(u);
                        }
                    }
                } else {
                    for (int u : reversedOut[v]) {
                        if (++cnt[u] == out[u].size()) {
                            res[u] = -1;
                            q.add(u);
                        }
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                writer.write((res[i] == 1 ? "FIRST" : (res[i] == -1 ? "SECOND" : "DRAW")) + "\n");
            }
            writer.write("\n");
        }

        writer.close();
        scn.close();
    }
}