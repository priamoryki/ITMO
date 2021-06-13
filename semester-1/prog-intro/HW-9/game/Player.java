package game;

/**
 * @author Pavel Lymar
 */
public interface Player {
    Move move(Position position, Cell cell);
}
