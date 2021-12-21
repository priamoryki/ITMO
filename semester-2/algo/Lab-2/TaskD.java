import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class TaskD {
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
        public int key, prior;
        public long sum;
        public Node left, right;

        Node() {

        }

        Node(int key) {
            this.key = key;
            this.prior = new Random().nextInt();
            this.sum = key;
        }
    }

    public static class PairOfNodes {
        Node first, second;

        PairOfNodes(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    public static long getSum(Node  p) {
        if (p == null)
            return 0;
        return p.sum;
    }

    public static Node update(Node p) {
        if (p == null)
            return null;
        p.sum = p.key + getSum(p.left) + getSum(p.right);
        return p;
    }

    public static class Treap {
        public Node root;

        public Node find(int value) {
            Node cur = root;
            while (cur != null) {
                if (cur.key < value) {
                    cur = cur.right;
                } else if (cur.key > value) {
                    cur = cur.left;
                } else {
                    break;
                }
            }
            return cur;
        }

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

        public static PairOfNodes split(Node t, int value, Node l, Node r) {
            if (t == null) {
                return new PairOfNodes(null, null);
            }
            if (t.key < value) {
                PairOfNodes pair = split(t.right, value, t.right, r);
                t.right = pair.first;
                r = pair.second;
                l = t;
            } else {
                PairOfNodes pair = split(t.left, value, l, t.left);
                l = pair.first;
                t.left = pair.second;
                r = t;
            }
            update(t);
            return new PairOfNodes(l, r);
        }

        public void insert(Node newNode) {
            if (find(newNode.key) != null) {
                return;
            }
            PairOfNodes pair = split(root, newNode.key, null, null);
            Node l = pair.first;
            Node r = pair.second;
            root = merge(merge(l, newNode), r);
        }

        public long sum(int i, int j) {
            PairOfNodes pair = split(root, i, null, null);
            Node t1 = pair.first;
            Node t2 = pair.second;

            pair = split(t2, j + 1, t2, null);
            t2 = pair.first;
            Node t3 = pair.second;

            long res = getSum(t2);
    
            root = merge(merge(t1, t2), t3);
            return res;
        }
    }

    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        Treap tree = new Treap();
        long last = -1;
        int n = scn.nextInt();
        for (int i = 0; i < n; i++) {
            String s = scn.next();
            int x = scn.nextInt();
            if (s.equals("+")) {
                if (last != -1) {
                    x = (int)((x + last) % 1_000_000_000);
                }
                tree.insert(new Node(x));
                last = -1;
            } else if (s.equals("?")) {
                int y = scn.nextInt();
                last = tree.sum(x, y);
                writer.println(last);
            }
        }
        writer.close();
        scn.close();
    }
}