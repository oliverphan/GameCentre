package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import fall2018.csc2017.common.BoardManager;

/**
 * Manage a slidingBoard, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingBoardManager extends BoardManager<SlidingBoard> {

    /**
     * Stores all of the moves that have been made so far.
     */
    private Stack<int[]> previousMoves;

    /**
     * Whether or not the image background is the default number pictures or
     * the User selected image.
     */
    boolean userTiles = false;

    /**
     * Number of Tiles.
     */
    private final int numTiles;

    /**
     * Manage a new shuffled slidingBoard with difficulty d.
     *
     * @param difficulty the difficulty.
     */
    public SlidingBoardManager(int difficulty) {
        super(difficulty);
        setName("Sliding Tiles");
        numTiles = difficulty * difficulty;
        previousMoves = new Stack<>();
        setDifficulty(difficulty);
    }

    /**
     * The score is calculated by (difficulty^2)*100 - (number of moves).
     * The score cannot be less than 0.
     *
     * @return the calculated score.
     */
    @Override
    public int generateScore() {
        return Math.max((((difficulty * difficulty) * 100) - numMoves), 0);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    protected boolean gameFinished() {
        Iterator<Tile> puzzleIterator = board.iterator();
        int curId = 1;
        Tile t = puzzleIterator.next();
        while (curId < board.numTiles()) {
            if (t.getId() != curId) {
                return false;
            }
            t = puzzleIterator.next();
            curId++;
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    protected boolean isValidTap(int position) {
        int row = position / board.getNumCols();
        int col = position % board.getNumCols();
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNumRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNumCols() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == numTiles)
                || (above != null && above.getId() == numTiles)
                || (left != null && left.getId() == numTiles)
                || (right != null && right.getId() == numTiles);
    }

    /**
     * Process a touch at position in the slidingBoard, swapping tiles as appropriate.
     *
     * @param position the position
     */
    protected void touchMove(int position) {
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        int[] move = findBlank(position);
        int[] cordPair = {row, col, move[0], move[1]};
        previousMoves.push(cordPair);
        numMoves += 1;
        board.swapTiles(row, col, move[0], move[1]);
    }

    /**
     * Set the difficulty for the slidingBoard, and
     * generate a new slidingBoard based on the difficulty.
     *
     * @param d the difficulty of the slidingBoard.
     */
    private void setDifficulty(int d) {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum, numTiles));
        }
        this.board = new SlidingBoard(tiles);
        shuffle(d);
    }

    /**
     * Shuffles tiles from solvable position so puzzle always has solution.
     *
     * @param d difficulty of slidingBoard
     */
    private void shuffle(int d) {
        int[] blankPos = {d - 1, d - 1};
        for (int i = 0; i < d * 100; i++) {
            ArrayList<int[]> moves = validShuffles(blankPos[0], blankPos[1]);
            int[] randomMove = moves.get(new Random().nextInt(moves.size()));
            this.board.swapTiles(blankPos[0], blankPos[1], randomMove[0], randomMove[1]);
            blankPos = randomMove;
        }
    }

    /**
     * Helper method for shuffling solvable slidingBoard.
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
        if (row != board.getNumRows() - 1) {
            int[] b = {row + 1, col};
            moves.add(b);
        }
        if (col != 0) {
            int[] l = {row, col - 1};
            moves.add(l);
        }
        if (col != board.getNumCols() - 1) {
            int[] r = {row, col + 1};
            moves.add(r);
        }
        return moves;
    }

    /**
     * Undo a given number of moves without updating the move counter. If there aren't enough
     * moves to undo, undo as many as possible and then do nothing.
     *
     * @param numToUndo the number of moves to undo.
     */
    void undoMove(int numToUndo) {
        if (!gameFinished()) {
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
        Iterator<Tile> blankIterator = board.iterator();
        int blankFoundAt = 0;
        if (isValidTap(position)) {
            while (blankIterator.next().getId() != numTiles) {
                blankFoundAt++;
            }
        }
        return new int[]{blankFoundAt / board.getNumRows(), blankFoundAt % board.getNumCols()};
    }


}