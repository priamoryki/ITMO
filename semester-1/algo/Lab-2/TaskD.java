import java.io.*;
import java.util.ArrayDeque;
 
public class TaskD {
    public static class Scanner {
        private BufferedReader reader;
        private char[] buffer = new char[1024];
        private int id = 0;
        private int inp = 0;
        public int numOfLines = 0;
    
        public Scanner(InputStream stream) {
            this.reader = new BufferedReader(new InputStreamReader(stream));
        }
    
        public Scanner(File file) {
            try {
                this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
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

    public static void main(String[] args) {
        ArrayDeque<Integer> l = new ArrayDeque<Integer>();
        ArrayDeque<Integer> r = new ArrayDeque<Integer>();
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int n = scn.nextInt();
        for (int i = 0; i < n; i++) {
            String s = scn.next();
            if (s.equals("+")) {
                r.addLast(scn.nextInt());
            } else if (s.equals("-")) {
                writer.println(l.pollFirst());
            } else if (s.equals("*")) {
                r.addFirst(scn.nextInt());
            }

            if (l.size() < r.size()) {
                l.addLast(r.pollFirst());
            }
        }
        writer.close();
        scn.close();
    }
}