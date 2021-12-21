import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskG {
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
        public int key, prior, size;
        public Node left, right;
 
        Node() {
 
        }
 
        Node(int key) {
            this.key = key;
            this.prior = new Random().nextInt();
            this.size = 1;
        }
    }

    public static class PairOfNodes {
        Node first, second;
 
        PairOfNodes(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    public static int getSize(Node p) {
        if (p == null) {
            return 0;
        }
        return p.size;
    }

    public static void update(Node t) {
        if (t == null) {
            return;
        }
        t.size = 1 + getSize(t.left) + getSize(t.right);
    }

    public static class Treap {
        public Node root;
        public ArrayList<Integer> arr = new ArrayList<>();

        public Node merge(Node l, Node r) {
            if (l == null) {
                return r;
            }
            if (r == null) {
                return l;
            }
            if (l.prior > r.prior) {
                l.right = merge(l.right, r);
                update(l);
                return l;
            } else {
                r.left = merge(l, r.left);
                update(r);
                return r;
            }
        }

        public PairOfNodes split(Node t, int x) {
            Node l, r;
            if (t == null) {
                return new PairOfNodes(null, null);
            }
            if (getSize(t.left) < x) {
                PairOfNodes pair = split(t.right, x - (getSize(t.left) + 1));
                t.right = pair.first;
                r = pair.second;
                l = t;
            } else {
                PairOfNodes pair = split(t.left, x);
                l = pair.first;
                t.left = pair.second;
                r = t;
            }
            update(t);
            return new PairOfNodes(l, r);
        }

        public void build(int n) {
            for (int i = 0; i < n; i++) {
                root = merge(root, new Node(i + 1));
            }
        }

        public Node toStart(Node t, int l, int r) {
            Node t1, t2, t3, t4;
            PairOfNodes pair = split(t, r + 1);
            t1 = pair.first;
            t2 = pair.second;
            pair = split(t1, l);
            t3 = pair.first;
            t4 = pair.second;
            return merge(merge(t4, t3), t2);
        }

        public void toArray(Node t) {
            if (t == null) {
                return;
            }
            toArray(t.left);
            arr.add(t.key);
            toArray(t.right);
        }
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        Treap tree = new Treap();
        int n = scn.nextInt();
        int m = scn.nextInt();
        tree.build(n);
        for (int i = 0; i < m; i++) {
            int l = scn.nextInt();
            int r = scn.nextInt();
            tree.toStart(tree.root, l - 1, r - 1);
        }
        tree.toArray(tree.root);
        for (int i = 0; i < tree.arr.size(); i++) {
            writer.write(tree.arr.get(i) + " ");
        }
        writer.close();
        scn.close();
    }
}