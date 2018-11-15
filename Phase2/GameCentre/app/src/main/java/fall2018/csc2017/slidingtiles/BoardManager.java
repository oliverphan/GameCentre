package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The number of moves made so far.
     */
    private int numMoves;

    /**
     * Stores all of the moves that have been made so far.
     */
    private Stack<int[]> previousMoves;

    /**
     * The number of tiles per side in the board.
     */
    private int difficulty;

    public boolean userTiles = false;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        numMoves = 0;
        previousMoves = new Stack<>();
        this.board = board;
        difficulty = board.numCols;
    }

    /**
     * The name of this game is "Sliding Tiles".
     *
     * @return the name of the game.
     */
    public String getName() {
        return "Sliding Tiles";
    }

    /**
     * Return the board.
     *
     * @return the board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return the difficulty.
     *
     * @return the difficulty.
     */
    int getDifficulty() {
        return difficulty;
    }

    /**
     * Return the number of moves made so far.
     *
     * @return the number of moves.
     */
    int getNumMoves() {
        return numMoves;
    }

    /**
     * The score is calculated by difficulty*100 - (number of moves). The score cannot be less than
     * 0.
     *
     * @return the calculated score.
     */
    int generateScore() {
        return Math.max(((difficulty * 100) - numMoves), 0);
    }

    /**
     * Set the difficulty for the board, and generate a new board based on the difficulty.
     *
     * @param d the difficulty of the board.
     */
    private void setDifficulty(int d) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = d * d;
        for (int tileNum = 1; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(0));
        this.board = new Board(tiles);
        shuffle(d);
    }

    /**
     * Shuffles tiles from solvable position so puzzle always has solution.
     *
     * @param d difficulty of board
     */
    private void shuffle(int d) {
        int[] blankPos = {d - 1, d - 1};
        for (int i = 0; i < d * 100; i++) {
            ArrayList<int[]> moves = validShuffles(blankPos[0], blankPos[1]);
            int[] randomMove = moves.get(new Random().nextInt(moves.size()));
            board.swapTiles(blankPos[0], blankPos[1], randomMove[0], randomMove[1]);
            blankPos = randomMove;
        }
    }

    /**
     * Helper method for shuffling solvable board.
     *
     * @param row row of target tile
     * @param col column of target tile
     * @return list of valid moves for target tile
     */
    private ArrayList<int[]> validShuffles(int row, int col) {
        ArrayList<int[]> moves = new ArrayList<>();
        if (row != 0) {
            int[] a = {row - 1, col};
            moves.add(a);
        }
        if (row != board.numRows - 1) {
            int[] b = {row + 1, col};
            moves.add(b);
        }
        if (col != 0) {
            int[] l = {row, col - 1};
            moves.add(l);
        }
        if (col != board.numCols - 1) {
            int[] r = {row, col + 1};
            moves.add(r);
        }
        return moves;
    }


    /**
     * Manage a new shuffled board with difficulty d.
     *
     * @param d the difficulty.
     */
    BoardManager(int d) {
        numMoves = 0;
        difficulty = d;
        previousMoves = new Stack<>();
        setDifficulty(difficulty);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> puzzleIterator = board.iterator();
        int curId = 1;
        Tile t = puzzleIterator.next();
        while (curId < board.numTiles()) {
            if (t.getId() != curId) {
                solved = false;
            }
            t = puzzleIterator.next();
            curId++;
        }
        return (solved && t.getId() == 0);
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / board.numCols;
        int col = position % board.numCols;
        int blankId = 0;
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.numRows - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.numCols - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }


    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / board.numRows;
        int col = position % board.numCols;
        int[] move = findBlank(position);
        int[] coordPair = {row, col, move[0], move[1]};
        previousMoves.push(coordPair);
        numMoves += 1;
        board.swapTiles(row, col, move[0], move[1]);
    }

    /**
     * Undo a given number of moves without updating the move counter. If there aren't enough
     * moves to undo, undo as many as possible and then do nothing.
     *
     * @param numToUndo the number of moves to undo.
     */
    void undoMove(int numToUndo) {
        if (!puzzleSolved()) {
            for (int i = 0; i < numToUndo; i++) {
                // Break if previous moves stack is empty
                if (previousMoves.isEmpty())
                    return;
                int[] tmp = previousMoves.pop();
                board.swapTiles(tmp[0], tmp[1], tmp[2], tmp[3]);
            }
        }
    }

    /**
     * Finds the row and column of the blank tile to setup the valid swap.
     *
     * @param position the clicked position.
     * @return int[row, column] of the blank tile.
     */
    private int[] findBlank(int position) {
        int blankId = 0;
        Iterator<Tile> blankIterator = board.iterator();
        int blankFoundAt = 0;
        if (isValidTap(position)) {
            while (blankIterator.next().getId() != blankId) {
                blankFoundAt++;
            }
        }
        return new int[]{blankFoundAt / board.numRows, blankFoundAt % board.numCols};
    }


}