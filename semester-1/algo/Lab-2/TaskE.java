import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
 
public class TaskE {
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
        Stack a = new Stack();
        Scanner scn = new Scanner(System.in);
        while (scn.hasNext()) {
            String s = scn.next();
            if (s.equals("+")) {
                int x = a.getLast();
                a.deleteLastItem();
                int y = a.getLast();
                a.deleteLastItem();
                a.append(x + y);
            } else if (s.equals("-")) {
                int x = a.getLast();
                a.deleteLastItem();
                int y = a.getLast();
                a.deleteLastItem();
                a.append(y - x);
            } else if (s.equals("*")) {
                int x = a.getLast();
                a.deleteLastItem();
                int y = a.getLast();
                a.deleteLastItem();
                a.append(x * y);
            } else {
                a.append(Integer.parseInt(s));
            }
        }
        System.out.println(a.getLast());
        scn.close();
    }
}