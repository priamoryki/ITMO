package game;

/**
 * @author Pavel Lymar
 */
public class SequentialPlayer implements Player {
	private final int m, n;
	
    public SequentialPlayer(final int m, final int n) {
        this.m = m;
        this.n = n;
    }
	
    @Override
    public Move move(final Position position, final Cell cell) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}