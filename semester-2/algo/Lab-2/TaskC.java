import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskC {
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
        public boolean zero;

        Node(int key) {
            this.key = key;
            this.prior = new Random().nextInt();
            this.size = 1;
            this.zero = (key == 0);
        }
    }

    public static class PairOfNodes {
        Node first, second;

        PairOfNodes(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void update(Node t) {
        if (t != null) {
            t.size = 1 + getSize(t.left) + getSize(t.right);
            t.zero = getZero(t.left) || getZero(t.right) || (t.key == 0);
        }
    }

    public static int getSize(Node t) {
        if (t == null) {
            return 0;
        } else {
            return t.size;
        }
    }

    public static boolean getZero(Node t) {
        if (t == null) {
            return false;
        } else {
            return t.zero;
        }
    }

    public static class Treap {
        public ArrayList<Integer> arr = new ArrayList<>();
        public Node root;
        
        public static Node merge(Node x, Node y) {
            if (x == null) {
                return y;
            } else if (y == null) {
                return x;
            }
            if (x.prior > y.prior) {
                x.right = merge(x.right, y);
                update(x);
                return x;
            }
            y.left = merge(x, y.left);
            update(y);
            return y;
        }

        public PairOfNodes split(Node t, int k) {
            if (k == getSize(t.left) + 1) {
                Node pair = t.right;
                t.right = null;
                update(t);
                return new PairOfNodes(t, pair);
            }
            if (k > getSize(t.left) + 1) {
                PairOfNodes pair = split(t.right, k - getSize(t.left) - 1);
                t.right = pair.first;
                update(t);
                return new PairOfNodes(t, pair.second);
            }
            if (k == 0) {
                return new PairOfNodes(null, t);
            }
            PairOfNodes pair = split(t.left, k);
            t.left = pair.second;
            update(t);
            return new PairOfNodes(pair.first, t);
        }

        public Node deleteZero(Node t) {
            if (t == null) {
                return null;
            } else if (getZero(t.left)) {
                t.left = deleteZero(t.left);
            } else if (t.key == 0) {
                return merge(t.left, t.right);
            } else if (getZero(t.right)) {
                t.right = deleteZero(t.right);
            }
            update(t);
            return t;
        }

        public void toArr(Node v) {
            if (v == null) {
                return;
            }
            toArr(v.left);
            arr.add(v.key);
            toArr(v.right);
        }

        public void insert(int k, int i) {
            PairOfNodes pair = split(root, k - 1);
            pair.first = merge(pair.first, new Node(i));
            pair.second = deleteZero(pair.second);
            root = merge(pair.first, pair.second);
        }

        public void build(int n) {
            for (int i = 0; i < n; i++) {
                root = merge(root, new Node(0));
            }
        }
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        Treap tree = new Treap();
        int n = scn.nextInt();
        int m = scn.nextInt();
        tree.build(n + m);
        for (int i = 1; i <= n; i++) {
            int k = scn.nextInt();
            tree.insert(k, i);
        }
        tree.toArr(tree.root);
        ArrayList<Integer> result = tree.arr;
        int s = result.size();
        for (int i = s - 1; i >= 0; i--) {
            if (result.get(s - 1) == 0) {
                s--;
            } else {
                break;
            }
        }
        writer.println(s);
        for (int i = 0; i < s; i++) {
            writer.print(result.get(i) + " ");
        }
        writer.close();
        scn.close();
    }
}