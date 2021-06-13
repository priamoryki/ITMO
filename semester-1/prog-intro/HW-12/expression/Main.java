package expression;

import java.util.Scanner;

/**
 * @author Pavel Lymar
 */
public class Main {
    public static void main(String[] args) {
        Add f = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)
        );

        System.out.println(f);

        Scanner scn = new Scanner(System.in);
        int i = scn.nextInt();
        scn.close();

        System.out.println(f.evaluate(i));
    }
}