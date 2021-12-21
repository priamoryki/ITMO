import java.io.*;

public class TaskB {
    public static Node root;
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
        public int key, height;
        public Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    public static int height(Node x) {
        if (x == null) {
            return 0;
        }
        return x.height;
    }

    public static int balanceFactor(Node x) {
        return height(x.right) - height(x.left);
    }

    public static void fixHeight(Node x) {
        int leftHeight = height(x.left);
        int rigthHeight = height(x.right);
        if (leftHeight > rigthHeight) {
            x.height = leftHeight + 1;
        } else {
            x.height = rigthHeight + 1;
        }
    }

    public static Node rightRotate(Node x) {
        Node l = x.left;
        x.left = l.right;
        l.right = x;
        fixHeight(x);
        fixHeight(l);
        return l;
    }

    public static Node leftRotate(Node x) {
        Node r = x.right;
        x.right = r.left;
        r.left = x;
        fixHeight(x);
        fixHeight(r);
        return r;
    }

    public static Node balance(Node x) {
        fixHeight(x);
        if(balanceFactor(x)== 2) {
            if(balanceFactor(x.right) < 0) {
                x.right = rightRotate(x.right);
            }
            return leftRotate(x);
        }
        if(balanceFactor(x) == -2) {
            if(balanceFactor(x.left) > 0) {
                x.left = leftRotate(x.left);
            }
            return rightRotate(x);
        }
        return x;
    }

    public static Node insert(Node x, Node newNode) {
        if (x == null) {
            return newNode;
        } else if (newNode.key < x.key) {
            x.left = insert(x.left, newNode);
        } else if (newNode.key> x.key) {
            x.right = insert(x.right, newNode);
        }
        return balance(x);
    }

    public static Node minimum(Node x) {
        if (x.left == null) {
            return x;
        }
        return minimum(x.left);
    }

    public static Node removeMin(Node x) {
        if(x.left == null) {
            return x.right;
        }
        x.left = removeMin(x.left);
        return balance(x);
    }

    public static Node delete(Node x, int value) {
        if (x == null) {
            return x;
        }
        if (value < x.key) {
            x.left = delete(x.left, value);
        } else if (value > x.key) {
            x.right = delete(x.right, value);
        } else {
            Node q = x.left;
            Node r = x.right;
            if(r == null) {
                return q;
            }
            Node min = minimum(r);
            min.right = removeMin(r);
            min.left = q;
            return balance(min);
        }
        return balance(x);
    }

    public static Node exists(Node x, int value) {
        if (x == null || value == x.key) {
            return x;
        }
        if (value < x.key) {
            return exists(x.left, value);
        } else {
            return exists(x.right, value);
        }
    }

    public static Node next(Node v, int x) {
        if (v == null) {
            return v;
        }
        if (v.key > x) {
            Node left = next(v.left, x);
            if (left == null) {
                return v;
            }
            if (left.key < v.key) {
                return left;
            } else {
                return v;
            }
        } else {
            return next(v.right, x);
        }
    }

    public static Node prev(Node v, int x) {
        if (v == null) {
            return v;
        }
        if (v.key < x) {
            Node right = prev(v.right, x);
            if (right == null) {
                return v;
            }
            if (right.key > v.key) {
                return right;
            } else {
                return v;
            }
        } else {
            return prev(v.left, x);
        }
    }

    public static void debug(Node x) {
        if (x != null) {
            System.out.println(x.key);
            debug(x.left);
            debug(x.right);
        }
    }

    public static void main(String[] args){
        String test = "insert 2 insert 4 delete 2 prev 2 next 2 prev 3";
        //Scanner scn = new Scanner(new ByteArrayInputStream(test.getBytes()));
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        while(scn.hasNext()) {
            String s = scn.next();
            if (scn.hasNext()) {
                int x = scn.nextInt();
                if (s.equals("insert")) {
                    root = insert(root, new Node(x));
                } else if (s.equals("delete")) {
                    root = delete(root, x);
                } else if (s.equals("exists")) {
                    if (exists(root, x) != null) {
                        writer.println("true");
                    } else {
                        writer.println("false");
                    }
                } else if (s.equals("next")) {
                    Node v = next(root, x);
                    if (v == null) {
                        writer.println("none");
                    } else {
                        writer.println(v.key);
                    }
                } else if (s.equals("prev")) {
                    Node v = prev(root, x);
                    if (v == null) {
                        writer.println("none");
                    } else {
                        writer.println(v.key);
                    }
                }
            }
        }
        writer.close();
        scn.close();
    }
}