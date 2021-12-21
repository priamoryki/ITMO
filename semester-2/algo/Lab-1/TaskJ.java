import java.io.*;

public class TaskJ {
    public static int n;
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
        public int min, max;
        Node(int min, int max) {
            this.min = min;
            this.max = max;
        }

        Node() {

        }
    }

    public static void update(int l, int v, int x, int lx, int rx) {
        if (rx < l || l < lx)
            return;
        if (rx == lx) {
            tree[x] = new Node(v, v);
            return;
        }
        int m = (lx + rx) / 2;
        update(l, v, x * 2, lx, m);
        update(l, v, x * 2 + 1, m + 1, rx);
        tree[x] = new Node(Math.min(tree[2 * x].min, tree[2 * x + 1].min), Math.max(tree[2 * x].max, tree[2 * x + 1].max));
    }

    public static int get(int l, int r, int val, int x, int lx, int rx) {
        if (rx < l || r < lx || val < tree[x].min)
            return 0;
        if (tree[x].max <= val && rx <= r && lx >= l) {
            for (int i = lx; i <= rx; i++) {
                update(i, Integer.MAX_VALUE, 1, 0, n - 1);
            }
            return rx - lx + 1;
        }
        int m = (lx + rx) / 2;
        int result = 0;
        if (tree[2 * x].min <= val) {
            result += get(l, r, val, 2 * x, lx, m);
        }
        if (tree[2 * x + 1].min <= val) {
            result += get(l, r, val, 2 * x + 1, m + 1, rx);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        n = scn.nextInt();
        int m = scn.nextInt();
        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        PrintWriter writer = new PrintWriter(System.out);
        for (int i = 0; i < m; i++) {
            int x = scn.nextInt();
            int l = scn.nextInt();
            int r = scn.nextInt();
            if (x == 1) {
                update(l, r, 1, 0, n - 1);
            } else if (x == 2) {
                int v = scn.nextInt();
                writer.write(Integer.toString(get(l, r - 1, v, 1, 0, n - 1)));
                writer.write('\n');
            }
        }
        writer.close();
        scn.close();
    }
}