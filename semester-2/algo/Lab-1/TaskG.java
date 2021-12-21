import java.io.*;

public class TaskG {
    public static int n, N;
    public static long[] a, tree;

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

    public static void build(int i) {
        if (i >= N - 1) {
            if (i >= N + n - 1) {
                tree[i] = Long.MAX_VALUE;
            }
            return;
        }
        build(2 * i + 1);
        build(2 * i + 2);
        tree[i] = Math.min(tree[2 * i + 1], tree[2 * i + 2]); 
    }

    public static void push(int i) {
        if (a[i] != Long.MAX_VALUE) {
            a[2 * i + 1] = a[i];
            tree[2 * i + 1] = a[i];
            a[2 * i + 2] = a[i];
            tree[2 * i + 2] = a[i];
        }
        a[i] = Long.MAX_VALUE;
    }
     
    public static long get(int l, int r, int i, int lx, int rx) {
        if (l >= rx || lx >= r) {
            return Long.MAX_VALUE;
        }
        if (lx >= l && rx <= r) {
            return tree[i];
        }
        push(i);
        int m = (lx + rx) / 2;
        return Math.min(get(l, r, 2 * i + 1, lx, m), get(l, r, 2 * i + 2, m, rx));
    }

    public static void set(int l, int r, int v, int i, int lx, int rx) {
        if (l >= rx || lx >= r) {
            return;
        }
        if (lx >= l && rx <= r) {
            a[i] = v;
            tree[i] = v;
            return;
        }
        push(i);
        int m = (lx + rx) / 2;
        set(l, r, v, 2 * i + 1, lx, m);
        set(l, r, v, 2 * i + 2, m, rx);
        tree[i] = Math.min(tree[2 * i + 1], tree[2 * i + 2]);
    }

    public static int nextPowOfTwo(int n) {
        int result = 1;
        while (result < n) {
            result *= 2;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        n = scn.nextInt();
        int m = scn.nextInt();
        N = nextPowOfTwo(n);
        a = new long[2 * N];
        tree = new long[2 * N];
        for (int i = 0; i < 2 * N; ++i) {
            a[i] = Long.MAX_VALUE;
        }
        for (int i = N - 1; i < N + n - 1; i++) {
            tree[i] = 0;
        }
        build(0);
        PrintWriter writer = new PrintWriter(System.out);
        for (int i = 0; i < m; i++) {
            int x = scn.nextInt();
            int l = scn.nextInt();
            int r = scn.nextInt();
            if (x == 1) {
                int v = scn.nextInt();
                set(l + N - 1, r + N - 1, v, 0, N - 1, 2 * N - 1);
            } else if (x == 2) {
                writer.write(Long.toString(get(l + N - 1, r + N - 1, 0, N - 1, 2 * N - 1)));
                writer.write('\n');
            }
        }
        writer.close();
        scn.close();
    }
}