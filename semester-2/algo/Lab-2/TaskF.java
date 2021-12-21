import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskF {
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
 
    public static Node update(Node p) {
        if (p == null) {
            return null;
        }
        p.size = 1 + getSize(p.left) + getSize(p.right);
        return p;
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

        public void add(Node newNode) {
            root = merge(root, newNode);
        }

        public void insert(int i, int value) {
            PairOfNodes pair = split(root, i);
            root = merge(pair.first, new Node(value));
            root = merge(root, pair.second);
        }

        public void remove(int k) {
            PairOfNodes pair1 = split(root, k - 1);
            PairOfNodes pair2 = split(pair1.second,  1);
            root = merge(pair1.first, pair2.second);
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
        for (int i = 0; i < n; ++i) {
            int j = scn.nextInt();
            tree.add(new Node(j));
        }
        for (int i = 0; i < m; i++) {
            String s = scn.next();
            int x = scn.nextInt();
            if (s.equals("add")) {
                int y = scn.nextInt();
                tree.insert(x, y);
            } else if (s.equals("del")) {
                tree.remove(x);
            }
        }
        tree.toArray(tree.root);
        writer.println(tree.arr.size());
        for (int i = 0; i < tree.arr.size(); i++) {
            writer.print(tree.arr.get(i) + " ");
        }
        writer.close();
        scn.close();
    }
}