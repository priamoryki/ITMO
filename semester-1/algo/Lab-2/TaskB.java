import java.io.*;
import java.util.Arrays;

public class TaskB {
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

        public void changeLastItem(int x) {
            nums[length - 1] = x;
        }
    
        public int getLast() {
            if (length != 0) {
                return nums[length - 1];
            } else {
                return Integer.MIN_VALUE;
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
        Stack nums = new Stack();
        Stack counter = new Stack();
        Scanner scn = new Scanner(System.in);
        int res = 0;
        int n = Integer.parseInt(scn.next());
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(scn.next());
            if (x == nums.getLast()) {
                counter.changeLastItem(counter.getLast() + 1);
            } else {
                if (counter.getLast() >= 3) {
                    res += counter.getLast();
                    counter.deleteLastItem();
                    nums.deleteLastItem();
                }
                if (x == nums.getLast()) {
                    counter.changeLastItem(counter.getLast() + 1);
                } else {
                    nums.append(x);
                    counter.append(1);
                }
            }
        }
        if (counter.getLast() >= 3) {
            res += counter.getLast();
        }
        scn.close();
        PrintWriter writer = new PrintWriter(System.out);
        writer.println(res);
        writer.close();
    }
}