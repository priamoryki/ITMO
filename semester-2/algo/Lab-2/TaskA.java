import java.io.*;

public class TaskA {
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
        public int key;
        public Node parent, left, right;

        Node(int key, Node parent) {
            this.key = key;
            this.parent = parent;
        }
    }

    public static void insert(Node root, Node newNode) {
        while (root != null) {
            if (newNode.key > root.key) {
                if (root.right != null) {
                    root = root.right;
                } else {
                    newNode.parent = root;
                    root.right = newNode;
                    break;
                }
            } else if (newNode.key < root.key) {
                if (root.left != null) {
                    root = root.left;
                } else {
                    newNode.parent = root;
                    root.left = newNode;
                    break;
                }
            } else {
                break;
            }
        }
    }

    public static Node minimum(Node x) {
        if (x.left == null) {
            return x;
        }
        return minimum(x.left);
    }

    public static Node delete(Node root, int value) {
        if (root == null) {
            return root;
        }
        if (value < root.key) {
            root.left = delete(root.left, value);
        } else if (value > root.key) {
            root.right = delete(root.right, value);
        } else if (root.left != null && root.right != null) {
            root.key = minimum(root.right).key;
            root.right = delete(root.right, root.key);
        } else {
            if (root.left != null) {
                root = root.left;
            } else if (root.right != null) {
                root = root.right;
            } else {
                root = null;
            }
        }
        return root;
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
        boolean isEmpty = true;
        //Scanner scn = new Scanner(new ByteArrayInputStream(test.getBytes()));
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        while(scn.hasNext()) {
            String s = scn.next();
            if (scn.hasNext()) {
                int x = scn.nextInt();
                if (s.equals("insert")) {
                    if (isEmpty) {
                        root = new Node(x, null);
                        isEmpty = false;
                    } else {
                        insert(root, new Node(x, null));
                    }
                } else if (s.equals("delete")) {
                    root = delete(root, x);
                    if (root == null) {
                        isEmpty = true;
                    }
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