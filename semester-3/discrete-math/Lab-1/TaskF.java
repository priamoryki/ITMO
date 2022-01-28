import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Pavel Lymar
 */
public class TaskF {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
    private static final ArrayList<Integer> prufersCode = new ArrayList<>();
    private static int[] power;
    private static int n;

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

    public static class Pair {
        private final int x, y;

        Pair(int u, int v) {
            this.x = u;
            this.y = v;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public boolean equals(Object other) {
            Pair oth = null;
            if (other instanceof Pair) {
                oth = (Pair) other;
            }
            return oth != null && this.x == oth.x && this.y == oth.y;
        }

        @Override
        public String toString() {
            return this.x + " " + this.y;
        }
    }

    private static ArrayList<Pair> getTree() {
        ArrayList<Pair> result = new ArrayList<>();
        int p = 1;
        while (p <= n && power[p] != 1) {
            p++;
        }
        int node = p;
        for (int i : prufersCode) {
            result.add(new Pair(node, i));
            power[i]--;
            if (power[i] == 1 && i < p) {
                node = i;
            } else {
                p++;
                while (p <= n && power[p] != 1) {
                    p++;
                }
                node = p;
            }
        }
        result.add(new Pair(node, n));
        return result;
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        power = new int[n + 1];
        Arrays.fill(power, 1);

        for (int i = 0; i < n - 2; i++) {
            int code = scn.nextInt();
            prufersCode.add(code);
            power[code]++;
        }

        for (Pair p : getTree()) {
            writer.write(p + "\n");
        }

        scn.close();
        writer.close();
    }
}
