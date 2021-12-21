import java.io.*;
 
public class TaskH {
    public static Node[] tree;
 
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
 
    public static class Node {
        public long sum, set, add;
 
        public Node(long x, long y, long z) {
            this.sum = x;
            this.set = y;
            this.add = z;
        }
    }
 
    public static void pushSet(int v, int lx, int rx) {
        if (tree[v].set == Long.MAX_VALUE) {
            return;
        }
        tree[v].sum = (rx - lx) * tree[v].set;
        if (rx - lx != 1) {
            tree[2 * v + 1].set = tree[2 * v + 2].set = tree[v].set;
            tree[2 * v + 1].add = tree[2 * v + 2].add = 0;
        }
        tree[v].set = Long.MAX_VALUE;
    }
 
    public static void pushAdd(int v, int lx, int rx) {
        if (tree[v].add == 0) {
            return;
        }
        tree[v].sum += (rx - lx) * tree[v].add;
        if (rx - lx != 1) {
            tree[2 * v + 1].add += tree[v].add;
            tree[2 * v + 2].add += tree[v].add;
            int m = (lx + rx) / 2;
            pushSet(2 * v + 1, lx, m);
            pushSet(2 * v + 2, m, rx);
            tree[2 * v + 1].set = tree[2 * v + 2].set = Long.MAX_VALUE;
        }
        tree[v].add = 0;
    }
 
    public static void set(int l, int r, long value, int v, int lx, int rx) {
        if (tree[v].add == 0) {
            pushSet(v, lx, rx);
        } else {
            pushAdd(v, lx, rx);
        }
        if (l >= rx || r <= lx) {
            return;
        }
        if (lx >= l && rx <= r) {
            tree[v].set = value;
            if (tree[v].add == 0) {
                pushSet(v, lx, rx);
            } else {
                pushAdd(v, lx, rx);
            }
            return;
        }
        int m = (lx + rx) / 2;
        set(l, r, value, 2 * v + 1, lx, m);
        set(l, r, value, 2 * v + 2, m, rx);
        tree[v] = new Node(tree[2 * v + 1].sum + tree[2 * v + 2].sum, Long.MAX_VALUE, 0);
    }
 
    public static void add(int l, int r, long value, int v, int lx, int rx) {
        if (tree[v].add == 0) {
            pushSet(v, lx, rx);
        } else {
            pushAdd(v, lx, rx);
        }
        if (l >= rx || r <= lx) {
            return;
        }
        if (lx >= l && rx <= r) {
            tree[v].add = value;
            if (tree[v].add == 0) {
                pushSet(v, lx, rx);
            } else {
                pushAdd(v, lx, rx);
            }
            return;
        }
        int m = (lx + rx) / 2;
        add(l, r, value, 2 * v + 1, lx, m);
        add(l, r, value, 2 * v + 2, m, rx);
        tree[v] = new Node(tree[2 * v + 1].sum + tree[2 * v + 2].sum, Long.MAX_VALUE, 0);
    }
 
    public static long get(int l, int r, int v, int lx, int rx) {
        if (tree[v].add == 0) {
            pushSet(v, lx, rx);
        } else {
            pushAdd(v, lx, rx);
        }
        if (l >= rx || r <= lx) {
            return 0;
        }
        if (lx >= l && rx <= r) {
            return tree[v].sum;
        }
        int m = (lx + rx) / 2;
        return get(l, r, 2 * v + 1, lx, m) + get(l, r, 2 * v + 2, m, rx);
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
        int n = nextPowOfTwo(scn.nextInt());
        int m = scn.nextInt();
        tree = new Node[2 * n - 1];
        for (int i = 0; i < 2 * n - 1; i++) {
            tree[i] = new Node(0, Long.MAX_VALUE, 0);
        }
        PrintWriter writer = new PrintWriter(System.out);
        for (int i = 0; i < m; i++) {
            int x = scn.nextInt();
            int l = scn.nextInt();
            int r = scn.nextInt();
            if (x == 1) {
                int v = scn.nextInt();
                set(l, r, v, 0, 0, n);
            } else if (x == 2) {
                int v = scn.nextInt();
                add(l, r, v, 0, 0, n);
            } else if (x == 3) {
                writer.write(Long.toString(get(l, r, 0, 0, n)));
                writer.write('\n');
            }
        }
        writer.close();
        scn.close();
    }
}