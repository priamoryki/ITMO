package game;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Pavel Lymar
 */
public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    private final int k;
    private int numberOfEmptySells;
    
    private final Cell[][] cells;
    private Cell turn;

    public TicTacToeBoard(final int m, final int n, final int k) {
    	this.k = k;
        this.cells = new Cell[n][m];
        this.numberOfEmptySells = n * m;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }
    
    private int count(int row, int column, int i, int j) {
        int res = 0;
        while (res < k && row + i >= 0 && column + j >= 0 && row + i < cells.length && column + j < cells[0].length && cells[row + i][column + j] == turn) {
            res++;
            row += i;
            column += j;
        }
        return res;
    }
    
    private boolean checkWin(int row, int column, int dir1, int dir2) {
        return count(row, column, dir1, dir2) + count(row, column, -dir1, -dir2) + 1 >= k;
    }
    
    /*public int checkWin(Move move, int start, int end, int k) {
    	int max = 0;
    	int temp = 0;
    	for (int i = start; i < end; i++) {
    		Cell val = Cell.E;
    		if (k == 1) val = cells[move.getRow()][i];
    		if (k == 2) val = cells[i][move.getColumn()];
    		if (k == 3) val = cells[move.getRow() + i][move.getColumn() + i];
    		if (k == 4) val = cells[move.getRow() + i][move.getColumn() - i];
    		
    		if (val == turn) {
        		temp++;
        	} else {
        		temp = 0;
        	}
    		if (temp > max) {
        		max = temp;
        	}
    	}
    	return max;
    }*/

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        
        cells[move.getRow()][move.getColumn()] = move.getValue();
        numberOfEmptySells--;
        
        //O(k) check
        //Row and column checking//
        //int inRow = checkWin(move, Math.max(0, move.getColumn() - (k - 1)), Math.min(cells[0].length, move.getColumn() + k), 1);
        //int inColumn = checkWin(move, Math.max(0, move.getRow() - (k - 1)), Math.min(cells.length, move.getRow() + k), 2);
        //Checking diagonals//
        //int inDiag1 = checkWin(move, -Math.min(move.getRow(), move.getColumn()), Math.min(cells.length - move.getRow(), cells[0].length - move.getColumn()), 3);
        //int inDiag2 = checkWin(move, -Math.min(cells[0].length - move.getColumn() - 1, move.getRow()), Math.min(cells.length - move.getRow(), move.getColumn() + 1), 4);
        
        int row = move.getRow();
        int column = move.getColumn();
        if (checkWin(row, column, 0, 1) || checkWin(row, column, 1, 0) || checkWin(row, column, 1, 1) || checkWin(row, column, 1, -1)) {
           return Result.WIN;
        }
        /*if (inDiag1 >= k || inDiag2 >= k || inRow >= k || inColumn >= k) {
            return Result.WIN;
        }*/
        if (numberOfEmptySells == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < cells.length
                && 0 <= move.getColumn() && move.getColumn() < cells[0].length
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < cells[0].length; i++) {
        	sb.append(i);
        }
        for (int r = 0; r < cells.length; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < cells[0].length; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}