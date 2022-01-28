import java.io.*;

/**
 * @author Pavel Lymar
 */
public class TaskC {
    private static final Scanner scn = new Scanner(System.in);
    private static final PrintWriter writer = new PrintWriter(System.out);
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

    private static boolean compare(int i, int j) {
        writer.write(1 + " " + i + " " + j + "\n");
        writer.flush();
        return "YES".equals(scn.next());
    }

    private static void sort(int l, int m, int r, int[] arr) {
        int[] a = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;
        while (k < r - l + 1) {
            if (j == r + 1 || (i < m + 1 && compare(arr[i], arr[j]))) {
                a[k++] = arr[i++];
            } else {
                a[k++] = arr[j++];
            }
        }
        System.arraycopy(a, 0, arr, l, k);
    }

    private static void mergeSort(int l, int r, int[] arr) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(l, m, arr);
            mergeSort(m + 1, r, arr);
            sort(l, m, r, arr);
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }

        mergeSort(0, n - 1, arr);

        writer.write(0 + " ");
        for (int i : arr) {
            writer.write(i + " ");
        }

        scn.close();
        writer.close();
    }
}
