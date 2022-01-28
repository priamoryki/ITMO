package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    public static class State {
        private final int size;
        private int numberOfEmptySells;
        private boolean crossesMove;
        private String phase;
        private final String[][] cells;

        private State(int size) {
            this.size = size;
            this.numberOfEmptySells = size * size;
            this.phase = "RUNNING";
            this.crossesMove = true;
            this.cells = new String[size][size];
        }

        public boolean getCrossesMove() {
            return crossesMove;
        }

        public String[][] getCells() {
            return cells;
        }

        public int getSize() {
            return size;
        }

        public String getPhase() {
            return phase;
        }

        private boolean isValidMove(int row, int col) {
            return 0 <= row && row < size && 0 <= col && col < size && cells[row][col] == null;
        }

        private void makeMove(int row, int col) {
            if (phase.equals("RUNNING") && isValidMove(row, col)) {
                numberOfEmptySells--;
                cells[row][col] = crossesMove ? "X" : "O";
                checkWin(new Move(row, col, cells[row][col]));
                crossesMove = !crossesMove;
            }
        }

        private int count(Move move, int i, int j) {
            int res = 0;
            int row = move.getRow();
            int col = move.getColumn();
            String value = move.getValue();
            while (res < size && row + i >= 0 && col + j >= 0
                    && row + i < size
                    && col + j < size
                    && value.equals(cells[row + i][col + j])) {
                res++;
                row += i;
                col += j;
            }
            return res;
        }

        private boolean checkWin(Move move, int dir1, int dir2) {
            return count(move, dir1, dir2) + count(move, -dir1, -dir2) + 1 >= size;
        }

        private void checkWin (Move move) {
            String value = move.getValue();
            if (checkWin(move, 0, 1)
                    || checkWin(move, 1, 0)
                    || checkWin(move, 1, 1)
                    || checkWin(move, 1, -1)) {
                phase = move.getValue().equals("X") ? "WON_X" : "WON_O";
            } else if (numberOfEmptySells == 0) {
                phase = "DRAW";
            }
        }
    }

    public static class Move {
        private final int row, col;
        private final String value;

        private Move(int row, int col, String value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        private int getRow() {
            return row;
        }

        private int getColumn() {
            return col;
        }

        private String getValue() {
            return value;
        }
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State(3);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (state == null) {
            newGame(request, view);
        } else {
            view.put("state", state);
        }
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            String move = e.getKey();
            if (move.matches("cell_[0-9]{2}")) {
                state.makeMove(move.charAt(move.length() - 2) - '0', move.charAt(move.length() - 1) - '0');
            }
        }
        view.put("state", state);
    }
}
