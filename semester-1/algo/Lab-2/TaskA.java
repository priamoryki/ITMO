import java.io.*;
import java.util.Arrays;
 
public class TaskA {
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
    }
 
    public static class Stack {
        private int nums[];
        private int length;
    
        Stack() {
            this.nums = new int[1];
            this.length = 0;
        }
    
        public void deleteLastItem() {
            nums[--length] = Integer.MAX_VALUE;
        }
    
        public int getLast() {
            if (length != 0) {
                return nums[length - 1];
            } else {
                return Integer.MAX_VALUE;
            }
        }
        
        public void append(int x) {
            if (length == nums.length) {
                this.nums = Arrays.copyOf(nums, 2 * length);
            }
            nums[length++] = x;
        }
    }
 
    public static void main(String[] args) {
        Stack mins = new Stack();
        Scanner scn = new Scanner(System.in);
        PrintWriter writer = new PrintWriter(System.out);
        int n = Integer.parseInt(scn.next());
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(scn.next());
            if (x == 1) {
                mins.append(Math.min(Integer.parseInt(scn.next()), mins.getLast()));
            }
            if (x == 2) {
                mins.deleteLastItem();
            }
            if (x == 3) {
                writer.print(mins.getLast());
                writer.print('\n');
            }
        }
        writer.close();
        scn.close();
    }
}