import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TaskA {
    public static int n;
    public static boolean[][] m;

    public static class ArrayQueue {
        private Integer[] elements = new Integer[1];
        private int start = 0, end = 0;

        public ArrayQueue(int size) {
            elements = new Integer[size];
        }

        public void clear() {
            elements = new Integer[1];
            start = 0;
            end = 0;
        }

        public int size() {
            if (elements[end] == null) {
                return (end - start + elements.length) % elements.length;
            } else {
                return elements.length;
            }
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public void enqueue(Integer x) {
            assert x != null : "Invalid operation for this argument";
            if (size() == elements.length) {
                updateArray();
            }
            elements[end] = x;
            end = (end + 1) % elements.length;
        }

        private void updateArray() {
            // :NOTE: Руками
            Integer[] result = new Integer[2 * elements.length];
            System.arraycopy(elements, start, result, 0, elements.length - start);
            System.arraycopy(elements, 0, result, elements.length - start, end);
            end = size();
            start = 0;
            elements = result;
        }

        public Integer element() {
            assert !isEmpty() : "Invalid operation for empty queue";
            return elements[start];
        }

        public Integer dequeue() {
            Integer result = element();
            elements[start] = null;
            start = (start + 1) % elements.length;
            return result;
        }

        public void push(Integer x) {
            assert x != null : "Invalid operation for this argument";
            if (size() == elements.length) {
                updateArray();
            }
            elements[(start - 1 + elements.length) % elements.length] = x;
            start = (start - 1 + elements.length) % elements.length;
        }

        public Integer peek() {
            assert !isEmpty() : "Invalid operation for empty queue";
            return elements[(end - 1 + elements.length) % elements.length];
        }

        public Integer remove() {
            Integer result = peek();
            elements[(end - 1 + elements.length) % elements.length] = null;
            end = (end - 1 + elements.length) % elements.length;
            return result;
        }

        public Integer at(int i) {
            return elements[(start + i) % elements.length];
        }

        public void changeElementAt(int i, int value) {
            elements[(start + i) % elements.length] = value;
        }
    }

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

    public static boolean areConnected(int v, int u) {
        return m[v][u];
    }

    public static ArrayQueue cycle() {
        ArrayQueue q = new ArrayQueue(n);
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        for (int k = 0; k < (n - 1) * n; k++) {
            if (!areConnected(q.at(0), q.at(1))) {
                int i = 2;
                while (!areConnected(q.at(0), q.at(i)) || !areConnected(q.at(1), q.at(i + 1))) {
                    i++;
                }
                for (int j = 0; j < i / 2; j++) {
                    int temp = q.at(1 + j);
                    q.changeElementAt(1 + j, q.at(i - j));
                    q.changeElementAt(i - j, temp);
                }
            }
            q.enqueue(q.dequeue());
        }
        return q;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        n = scn.nextInt();
        m = new boolean[n][n];
        for (int i = 1; i < n; i++) {
            String s = scn.next();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '1') {
                    m[i][j] = m[j][i] = true;
                }
            }
        }
        ArrayQueue q = cycle();
        for (int i = 0; i < q.size(); i++) {
            writer.write((q.at(i) + 1) + " ");
        }
        writer.close();
        scn.close();
    }
}
