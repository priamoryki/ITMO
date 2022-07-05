import java.io.*;
import java.util.Arrays;

/**
 * @author Pavel Lymar
 */
public class TaskB {
    private static class Scanner implements AutoCloseable {
        private final BufferedReader reader;
        private final char[] buffer = new char[1024];
        private int id = 0;
        private int bufferSize = 0;

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

        private void updateBuffer() {
            try {
                bufferSize = reader.read(buffer);
                id = 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public boolean hasNext() {
            if (id >= bufferSize) {
                updateBuffer();
            }
            return bufferSize != -1;
        }

        public Character nextChar() {
            if (hasNext()) {
                return buffer[id++];
            }
            return null;
        }

        public String next() {
            StringBuilder result = new StringBuilder();
            while (hasNext()) {
                Character c = nextChar();
                if (!Character.isWhitespace(c)) {
                    result.append(c);
                } else if (result.length() != 0) {
                    break;
                }
            }
            return result.toString();
        }

        public String nextLine() {
            StringBuilder result = new StringBuilder();
            while (hasNext()) {
                Character c = nextChar();
                if (c.equals('\n')) {
                    break;
                }
                result.append(c);
            }
            return result.toString();
        }

        public int nextInt() {
            try {
                return Integer.parseInt(next());
            } catch (NumberFormatException e) {
                return Integer.MIN_VALUE;
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scn = new Scanner(System.in); PrintWriter writer = new PrintWriter(System.out)) {
            int n = scn.nextInt() + 1;
            int[][] g = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    g[i][j] = i == 0 || j == 0 ? 0 : scn.nextInt();
                }
            }
            int[] u = new int[n];
            int[] v = new int[n];
            int[] matching = new int[n];
            int[] path = new int[n];
            for (int i = 1; i < n; i++) {
                matching[0] = i;
                int[] minv = new int[n];
                Arrays.fill(minv, Integer.MAX_VALUE);
                boolean[] isUsed = new boolean[n];
                int j0 = 0;
                do {
                    isUsed[j0] = true;
                    int i0 = matching[j0], delta = Integer.MAX_VALUE, j1 = 0;
                    for (int j = 1; j < n; j++) {
                        if (!isUsed[j]) {
                            int diff = g[i0][j] - u[i0] - v[j];
                            if (diff < minv[j]) {
                                minv[j] = diff;
                                path[j] = j0;
                            }
                            if (minv[j] < delta) {
                                delta = minv[j];
                                j1 = j;
                            }
                        }
                    }
                    for (int j = 0; j < n; j++) {
                        if (isUsed[j]) {
                            u[matching[j]] += delta;
                            v[j] -= delta;
                        } else {
                            minv[j] -= delta;
                        }
                    }
                    j0 = j1;
                } while (matching[j0] != 0);
                do {
                    matching[j0] = matching[path[j0]];
                    j0 = path[j0];
                } while (j0 > 0);
            }
            writer.write(-1 * v[0] + "\n");
            for (int i = 1; i < n; i++) {
                writer.write(matching[i] + " " + i + "\n");
            }
        } catch (Exception ignored) {

        }
    }
}
