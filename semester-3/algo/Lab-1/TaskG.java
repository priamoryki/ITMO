import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskG {
    private static Scanner scn = new Scanner(System.in);
    private static PrintWriter writer = new PrintWriter(System.out);
    private static int n, m, color = 1;
    private static ArrayList<Integer>[] out = new ArrayList[2_500];
    private static ArrayList<Integer>[] invertedOut = new ArrayList[2_500];
    private static int[] comp = new int[2_500];
    private static boolean[] used = new boolean[2_500];
    private static HashMap<String, Integer> nameToNumber = new HashMap<>();
    private static HashMap<Integer, String> numberToName = new HashMap<>();
    private static HashMap<Integer, Integer> connectedNumbers = new HashMap<>();
    private static ArrayDeque<Integer> nums = new ArrayDeque<>();
    private static ArrayDeque<Integer> queue = new ArrayDeque<>();

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

    private static void dfs1(int v) {
        used[v] = true;
        for (int to : out[v]) {
            if (!used[to]) {
                dfs1(to);
            }
        }
        queue.push(v);
    }
    
    private static void dfs2(int v) {
        comp[v] = color;
        for (int to : invertedOut[v]) {
            if (comp[to] == 0) {
                dfs2(to);
            }
        }
    }

    public static void main(String[] args) {
        n = scn.nextInt();
        m = scn.nextInt();

        for (int i = 0; i < 2_500; i++) {
            out[i] = new ArrayList<>();
            invertedOut[i] = new ArrayList<>();
        }


        int num = 0;
        for (int i = 0; i < n; i++) {
            String name = scn.next();
            nums.add(num);
            nameToNumber.put("+" + name, num);
            numberToName.put(num, "+" + name);
            connectedNumbers.put(num, num + 1);
            num++;
            nameToNumber.put("-" + name, num);
            numberToName.put(num, "-" + name);
            connectedNumbers.put(num, num - 1);
            num++;
        }

        for (int i = 0; i < m; i++) {
            String name1 = scn.next();
            scn.next();
            String name2 = scn.next();
            int x = nameToNumber.get(name1);
            int y = nameToNumber.get(name2);
            out[x].add(y);
            out[connectedNumbers.get(y)].add(connectedNumbers.get(x));
            invertedOut[y].add(x);
            invertedOut[connectedNumbers.get(x)].add(connectedNumbers.get(y));
        }

        for (int i = 0; i < 2 * n; i++) {
            if (!used[i]) {
                dfs1(i);
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.pop();
            if (comp[v] == 0) {
                dfs2(v);
                color++;
            }
        }

        boolean isEnd = false;
        ArrayList<String> result = new ArrayList<>();
        for (int i : nums) {
            if (comp[i] == comp[connectedNumbers.get(i)]) {
                writer.write(-1 + "\n");
                isEnd = true;
                break;
            } else if (comp[i] > comp[connectedNumbers.get(i)]) {
                result.add(numberToName.get(i));
            }
        }
        if (!isEnd && result.isEmpty()) {
            writer.write(-1 + "\n");
            isEnd = true;
        }
        if (!isEnd) {
            writer.write(result.size() + "\n");
            for (String a : result) {
                writer.write(a.substring(1) + "\n");
            }
        }

        writer.close();
        scn.close();
    }
}