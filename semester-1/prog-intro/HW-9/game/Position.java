package game;

/**
 * @author Pavel Lymar
 */
public interface Position {
    boolean isValid(Move move);

    Cell getCell(int r, int c);
}
