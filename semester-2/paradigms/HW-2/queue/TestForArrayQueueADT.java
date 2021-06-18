package queue;

/**
 * @author Pavel Lymar
 */

public class TestForArrayQueueADT {
    public static void test() {
        ArrayQueueADT q = new ArrayQueueADT();
        System.out.println("ArrayQueueADT test:");
        fill(q);
        dump(q);
        ArrayQueueADT.clear(q);
        System.out.println("Is the queue empty: " + ArrayQueueADT.isEmpty(q));
    }

    private static void fill(ArrayQueueADT q) {
        for (int i = 5; i > 0; i--) {
            int x = 5 - i + 1;
            System.out.print(x + " ");
            q.push(q, i);
        }
        for (int i = 6; i <= 10; i++) {
            System.out.print(i + " ");
            q.enqueue(q, i);
        }
        System.out.println();
    }

    private static void dump(ArrayQueueADT q) {
        System.out.println("Size: " + ArrayQueueADT.size(q));
        for (int i = 0; i < 5; i++) {
            System.out.println("Last element: " + ArrayQueueADT.dequeue(q));
        }
        System.out.println("Size: " + ArrayQueueADT.size(q));
        for (int i = 0; i < 5; i++) {
            System.out.println("First element: " + ArrayQueueADT.remove(q));
        }
    }
}