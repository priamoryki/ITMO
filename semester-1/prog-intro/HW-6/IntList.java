import java.util.Arrays;

public class IntList {
    private int nums[];
    public int length;

    IntList(int[] a) {
        this.nums = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            nums[length++] = a[i];
        }
    }

    IntList(int x) {
        this.nums = new int[1];
        nums[length++] = x;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(length);
        for (int i = 0; i < length; i++) {
            s.append(" " + nums[i]);
        }
        return s.toString();
    }
    
    public int get(int i) {
        return nums[i];
    }
    
    public void append(int x) {
        if (length == nums.length) {
            this.nums = Arrays.copyOf(nums, 2 * length);
        }
        nums[length++] = x;
    }
}