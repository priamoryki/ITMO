import java.io.*;

public class TaskE {
    static int[] tree;
    static int n;
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

    public static void set(int i, int x) {
        i += n - 1;
        tree[i] = x;
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = Math.max(tree[2 * i + 1], tree[2 * i + 2]);
        }
    }

    public static int maxSimple(int val, int x) {
        if (x >= n - 1) {
            return x - n + 1;
        }
        if (tree[2 * x + 1] >= val) {
            return maxSimple(val, 2 * x + 1);
        } else if (tree[2 * x + 2] >= val) {
            return maxSimple(val, 2 * x + 2);
        }
        return -1;
    }

    public static int myMax(int val, int l, int r, int x, int lx, int rx) {
        if (tree[x] < val || l >= rx || lx >= r) {
            return -1;
        }
        if (lx >= l && rx <= r) {
            return maxSimple(val, x);
        }
        int m = (lx + rx) / 2;
        int k = myMax(val, l, r, 2 * x + 1, lx, m);
        if (k != -1) {
            return k;
        }
        k = myMax(val, l, r, 2 * x + 2, m, rx);
        return k;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int k = scn.nextInt();
        int m = scn.nextInt();
        n = (int)Math.pow(2, Math.ceil(Math.log(k) / Math.log(2)));
        tree = new int[2 * n];
        for (int i = 0; i < k; i++) {
            int b = scn.nextInt();
            set(i, b);
        }
        for (int i = k; i < n; i++) {
            set(i, 0);
        }
        PrintWriter writer = new PrintWriter(System.out);
        for (int i = 0; i < m; i++) {
            int x = scn.nextInt();
            int y = scn.nextInt();
            int z = scn.nextInt();
            if (x == 1) {
                set(y, z);
            } else if (x == 2) {
                writer.write(Integer.toString(myMax(y, z + n - 1, 2 * n - 1, 0, n - 1, 2 * n - 1)));
                writer.write('\n');
            }
        }
        writer.close();
        scn.close();
    }
}