package game;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Pavel Lymar
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            Reader reader = new Reader(in);
            int i = reader.readInt("Enter the number of row");
            int j = reader.readInt("Enter the number of colomn");
            final Move move = new Move(i, j, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid. Please, try again.");
        }
    }
}