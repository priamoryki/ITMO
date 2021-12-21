import java.io.*;

public class TaskC {
    static Node[] tree = new Node[3_000_000];

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
        long curSum, prefix, suffix, result;

        public Node() {

        }

        public Node(long value) {
            this.curSum = value;
            this.prefix = this.suffix = this.result = Math.max(0, value);
        }
    }

    public static void build(int[] a, int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = new Node(a[tl]);
        } else {
            int m = (tl + tr) / 2;
            build(a, v * 2, tl, m);
            build(a, v * 2 + 1, m + 1, tr);
            tree[v] = mixNodes(tree[v * 2], tree[v * 2 + 1]);
        }
    }

    public static void update(int v, int lx, int rx, int pos, int value) {
        if (lx == rx) {
            tree[v] = new Node(value);
        } else {
            int m = (lx + rx) / 2;
            if (pos <= m) {
                update(v * 2, lx, m, pos, value);
            } else {
                update(v * 2 + 1, m + 1, rx, pos, value);
            }
            tree[v] = mixNodes(tree[v * 2], tree[v * 2 + 1]);
        }
    }

    public static Node get(int v, int lx, int rx, int l, int r) {
        if (l == lx && rx == r) {
            return tree[v];
        }
        int m = (lx + rx) / 2;
        if (r <= m) {
            return get(v * 2, lx, m, l, r);
        } else if (l > m) {
            return get(v * 2 + 1, m + 1, rx, l, r);
        } else {
            return mixNodes(get(v * 2, lx, m, l, m), get(v * 2 + 1, m + 1, rx, m + 1, r));
        }
    }

    public static Node mixNodes(Node l, Node r) {
        Node res = new Node();
        res.curSum = l.curSum + r.curSum;
        res.prefix = Math.max(l.prefix, l.curSum + r.prefix);
        res.suffix = Math.max(r.suffix, r.curSum + l.suffix);
        res.result = Math.max(Math.max(l.result, r.result), l.suffix + r.prefix);
        return res;
    }

    public static long getResult(int n) {
        return get(1, 0, n - 1, 0, n - 1).result;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int m = scn.nextInt();
        int[] a = new int[n];
        PrintWriter writer = new PrintWriter(System.out);
        for (int i = 0; i < n; i++) {
            a[i] = scn.nextInt();
        }
        build(a, 1, 0, n - 1);
        writer.write(Long.toString(getResult(n)));
        writer.write('\n');
        for (int j = 0; j < m; j++) {
            int i = scn.nextInt();
            int v = scn.nextInt();
            update(1, 0, n - 1, i, v);
            writer.write(Long.toString(getResult(n)));
            writer.write('\n');
        }
        writer.close();
        scn.close();
    }
}