package game;

/**
 * @author Pavel Lymar
 */
public interface Board {
    Position getPosition();
    Cell getCell();
    Result makeMove(Move move);
}
