package game;

import java.util.Random;

/**
 * @author Pavel Lymar
 */
public class RandomPlayer implements Player {
    private final int m, n;
	private final Random random;
    
    public RandomPlayer(final int m, final int n) {
        this.m = m;
        this.n = n;
        this.random = new Random();
    }
    
    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(n);
            int c = random.nextInt(m);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}