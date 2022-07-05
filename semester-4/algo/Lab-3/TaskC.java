import java.io.*;

/**
 * @author Pavel Lymar
 */
public class TaskC {
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
            int r1 = scn.nextInt();
            int s1 = scn.nextInt();
            int p1 = scn.nextInt();
            int r2 = scn.nextInt();
            int s2 = scn.nextInt();
            int p2 = scn.nextInt();
            writer.write(Integer.toString(Math.max(0, Math.max(r1 - r2 - p2, Math.max(s1 - s2 - r2, p1 - p2 - s2)))));
        } catch (Exception ignored) {

        }
    }
}
