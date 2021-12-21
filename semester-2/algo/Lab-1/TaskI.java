import java.io.*;

public class TaskI {
    public static int n, N, r;
    public static Matrix[] a, tree;

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

    public static class Matrix {
        public int e11, e12, e21, e22;
        Matrix(int e11, int e12, int e21, int e22) {
            this.e11 = e11;
            this.e12 = e12;
            this.e21 = e21;
            this.e22 = e22;
        }

        Matrix() {

        }
    }

    public static Matrix multMatrix(Matrix a, Matrix b) {
        int x = ((a.e11 * b.e11) % r + (a.e12 * b.e21) % r) % r;
        int y = ((a.e11 * b.e12) % r + (a.e12 * b.e22) % r) % r;
        int x1 = ((a.e21 * b.e11) % r + (a.e22 * b.e21) % r) % r;
        int y1 = ((a.e21 * b.e12) % r + (a.e22 * b.e22) % r) % r;
        return new Matrix(x, y, x1, y1);
    }

    public static void build(int l, int r, int v) {
        if (r - l == 1) {
            tree[v] = a[l];
            return;
        }
        int m = (l + r) / 2;
        build(l, m, v * 2 + 1);
        build(m, r, v * 2 + 2);
        tree[v] = multMatrix(tree[v * 2 + 1], tree[v * 2 + 2]);
    }

    public static Matrix get(int v, int l, int r, int lx, int rx) {
        if (l >= rx || lx >= r) {
            // identity matrix
            return new Matrix(1, 0, 0, 1);
        }
        if (lx >= l && rx <= r) {
            return tree[v];
        }
        int m = (lx + rx) / 2;
        return multMatrix(get(v * 2 + 1, l, r, lx, m), get(v * 2 + 2, l, r, m, rx));
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        r = scn.nextInt();
        n = scn.nextInt();
        int m = scn.nextInt();
        a = new Matrix[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Matrix(scn.nextInt(), scn.nextInt(), scn.nextInt(), scn.nextInt());
        }
        tree = new Matrix[4 * n];
        for (int i = 0; i < 2 * n; i++) {
            tree[i] = new Matrix();
        }
        build(0, n, 0);
        PrintWriter writer = new PrintWriter(System.out);
        for (int i = 0; i < m; i++) {
            int l = scn.nextInt();
            int r = scn.nextInt();
            l--;
            Matrix res = get(0, l, r, 0, n);
            writer.write(res.e11 + " " + res.e12);
            writer.write('\n');
            writer.write(res.e21 + " " + res.e22);
            writer.write('\n');
            writer.write('\n');
        }
        writer.close();
        scn.close();
    }
}