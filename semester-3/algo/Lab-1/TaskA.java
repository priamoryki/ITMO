import java.io.*;
import java.util.ArrayList;

public class TaskA {
    private static int n, m;
    private static boolean cycle = false;
    private static ArrayList<Integer> topsort = new ArrayList<>();
    private static boolean[] mark = new boolean[100_000];
    private static boolean[] finished = new boolean[100_000];
    private static ArrayList<Integer>[] out = new ArrayList[100_000];

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

    private static void dfs(int v) {
        mark[v] = true;
        for (int u : out[v]) {
            if (!mark[u]){
                dfs(u);
            } else if (!finished[u]){
                cycle = true;
            }
        }
        topsort.add(v);
        finished[v] = true;
    }

    private static ArrayList<Integer> topsort() {
        for (int i = 0; i < n; i++) {
            if (!mark[i]) {
                dfs(i);
            }
        }
        ArrayList<Integer> newTopsort = new ArrayList<>();
        if (!cycle) {
            for (int i = topsort.size() - 1; i >= 0; i--) {
                newTopsort.add(topsort.get(i));
            }
        } else {
            newTopsort.add(-2);
        }
        topsort = newTopsort;
        return topsort;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        n = scn.nextInt();
        m = scn.nextInt();
        for (int i = 0; i < n; i++) {
            out[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int v = scn.nextInt();
            int u = scn.nextInt();
            out[v - 1].add(u - 1);
        }
        for (int i : topsort()) {
            writer.write((i + 1) + " ");
        }
        writer.close();
        scn.close();
    }
}