import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TaskB {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static int n;
    private static boolean[][] m;
    private static ArrayQueue q;

    public static class ArrayQueue {
        private Integer[] elements;
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
            assert i < size() : "Invalid operation for this size";
            elements[(start + i) % elements.length] = value;
        }

        public void reverse(int start, int end) {
            for (int i = 0; i < (end - start + 1) / 2; i++) {
                int temp = q.at(start + i);
                q.changeElementAt(start + i, q.at(end - i));
                q.changeElementAt(end - i, temp);
            }
        }
    }

    public static class Scanner {
        private final BufferedReader reader;
        private final char[] buffer = new char[1024];
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
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < n * (n - 1); ++i) {
            int first = q.at(0);
            int second = q.at(1);
            if (!areConnected(first, second)) {
                i = 2;
                while ((!areConnected(first, q.at(i)) || !areConnected(second, q.at(i + 1))) && (i < n)) {
                    i++;
                }
                if (i >= n) {
                    i = 2;
                    while (!areConnected(first, q.at(i))){
                        i++;
                    }
                }
                q.reverse(1, i);
            }
            q.enqueue(q.dequeue());
        }
        return q;
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = new boolean[n][n];
        q = new ArrayQueue(n);
        for (int i = 1; i < n; i++) {
            String s = scn.next();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '1') {
                    m[i][j] = m[j][i] = true;
                }
            }
        }
        q = cycle();
        for (int i = 0; i < q.size(); i++) {
            writer.write((q.at(i) + 1) + " ");
        }
        writer.close();
        scn.close();
    }
}
