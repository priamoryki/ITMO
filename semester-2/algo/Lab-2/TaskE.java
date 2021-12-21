import java.io.*;
import java.util.Random;
 
public class TaskE {
    public static int length = 0;
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
 
        public Node merge(Node l, Node r) {
            if (l == null) return update(r);
            if (r == null) return update(l);
    
            if (l.prior > r.prior) {
                l.right = merge(l.right, r);
                return update(l);
            } else {
                r.left = merge(l, r.left);
                return update(r);
            }
        }
 
        public static PairOfNodes split(Node t, int value) {
            Node l, r;
            if (t == null) {
                return new PairOfNodes(null, null);
            }
            if (t.key < value) {
                PairOfNodes pair = split(t.right, value);
                t.right = pair.first;
                r = pair.second;
                l = t;
            } else {
                PairOfNodes pair = split(t.left, value);
                l = pair.first;
                t.left = pair.second;
                r = t;
            }
            update(t);
            return new PairOfNodes(l, r);
        }
 
        public void insert(Node newNode) {
            PairOfNodes pair = split(root, newNode.key);
            Node l = pair.first;
            Node r = pair.second;
            root = merge(merge(l, newNode), r);
        }
 
        public Node remove(int key) {
            PairOfNodes pair1 = split(root, key);
            PairOfNodes pair2 = split(pair1.second, key + 1);
            return merge(pair1.first, pair2.second);
        }
 
        public int findKthMax(Node t, int key) {
            if (getSize(t.right) == key - 1) {
                return t.key;
            }
            if (t.right == null) {
                return findKthMax(t.left, key - 1);
            }
            if (t.right.size < key) {
                return findKthMax(t.left, key - (t.right.size + 1));
            }
            return findKthMax(t.right, key);
        }
 
        public void debug(Node p) {
            if (p == null) {
                return;
            }
            debug(p.left);
            System.out.println(p.key);
            debug(p.right);
        }
    }
 
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        String test = "20   +1 5   +1 3   +1 7   0 1   0 2   0 3   -1 5   +1 10   0 1   0 2   0 3   +1 8   +1 5   0 1   0 2   0 3   0 4   0 5   -1 8   0 4";
        //Scanner scn = new Scanner(new ByteArrayInputStream(test.getBytes()));
        PrintWriter writer = new PrintWriter(System.out);
        Treap tree = new Treap();
        int n = scn.nextInt();
        for (int i = 0; i < n; i++) {
            //System.out.println("DEBUG");
            //tree.debug(tree.root);
            int s = scn.nextInt();
            int x = scn.nextInt();
            if (s == 1) {
                tree.insert(new Node(x));
                length++;
            } else if (s == 0) {
                writer.println(tree.findKthMax(tree.root, x));
            } else if (s == -1) {
                tree.root = tree.remove(x);
                length--;
            }
        }
        writer.close();
        scn.close();
    }
}