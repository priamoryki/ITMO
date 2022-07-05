import java.io.*;

/**
 * @author Pavel Lymar
 */
public class TaskI {
    private static final int MOD = 104_857_601;

    private static class Scanner implements AutoCloseable {
        private final BufferedReader reader;
        private final char[] buffer = new char[1024];
        private int id = 0;
        private int bufferSize = 0;

        public Scanner(InputStream stream) {
            this.reader = new BufferedReader(new InputStreamReader(stream));
        }

        @Override
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

        public long nextLong() {
            try {
                return Long.parseLong(next());
            } catch (NumberFormatException e) {
                return Long.MIN_VALUE;
            }
        }
    }

    private static long[] polynomialMul(long[] a, long[] b) {
        long[] result = new long[a.length + b.length - 1];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                result[i + j] += (a[i] * b[j]) % MOD;
                result[i + j] %= MOD;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scn = new Scanner(System.in)) {
            int k = (int) scn.nextLong();
            long n = scn.nextLong() - 1;
            long[] a = new long[2 * k];
            for (int i = 0; i < k; i++) {
                a[i] = scn.nextLong();
            }
            long[] q = new long[k + 1];
            q[0] = 1;
            for (int i = 1; i < k + 1; i++) {
                q[i] = MOD - scn.nextLong();
            }

            while (n >= k) {
                for (int i = k; i < 2 * k; i++) {
                    for (int j = 1; j < k + 1; j++) {
                        a[i] += ((MOD - q[j]) * a[i - j]) % MOD;
                        a[i] %= MOD;
                    }
                }
                long[] negativeQ = new long[k + 1];
                for (int i = 0; i < k + 1; i++) {
                    negativeQ[i] = i % 2 == 0 ? q[i] : MOD - q[i];
                }
                long[] r = polynomialMul(q, negativeQ);
                int id = 0;
                for (int i = 0; i < a.length; i++) {
                    if (i % 2 == n % 2) {
                        a[id++] = a[i];
                    }
                }
                for (int i = id; i < a.length; i++) {
                    a[i] = 0;
                }
                for (int i = 0; i < k + 1; i++) {
                    q[i] = r[2 * i];
                }
                n /= 2;
            }

            System.out.println(a[(int) n]);
        }
    }
}
