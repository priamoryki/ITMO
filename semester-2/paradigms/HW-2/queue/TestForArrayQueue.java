package queue;

/**
 * @author Pavel Lymar
 */

public class TestForArrayQueue {
    public static void test() {
        ArrayQueue q = new ArrayQueue();
        System.out.println("ArrayQueue test:");
        fill(q);
        dump(q);
        q.clear();
        System.out.println("Is the queue empty: " + q.isEmpty());
    }

    private static void fill(ArrayQueue q) {
        for (int i = 5; i > 0; i--) {
            int x = 5 - i + 1;
            System.out.print(x + " ");
            q.push(i);
        }
        for (int i = 6; i <= 10; i++) {
            System.out.print(i + " ");
            q.enqueue(i);
        }
        System.out.println();
    }
    
    private static void dump(ArrayQueue q) {
        System.out.println("Size: " + q.size());
        for (int i = 0; i < 5; i++) {
            System.out.println("Last element: " + q.dequeue());
        }
        System.out.println("Size: " + q.size());
        for (int i = 0; i < 5; i++) {
            System.out.println("First element: " + q.remove());
        }
    }
}