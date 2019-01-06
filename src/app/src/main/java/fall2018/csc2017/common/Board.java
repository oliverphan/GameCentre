package fall2018.csc2017.common;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.connectfour.Piece;
import fall2018.csc2017.matchingcards.Card;
import fall2018.csc2017.slidingtiles.Tile;

@SuppressWarnings("unchecked")
public abstract class Board<T> extends Observable implements Serializable, Iterable<T> {

    /**
     * The number of rows in this Board.
     */
    protected int numRows;

    /**
     * The number of columns in this Board.
     */
    protected int numCols;

    /**
     * A 2D array of Tokens to be placed in a Board
     */
    protected T[][] tokens;


    /**
     * Constructs a Board (Connect Four) filled of type T.
     */
    protected Board() {
        numCols = 7;
        numRows = 6;
        T tmp = (T) new Piece();
        this.tokens = (T[][]) Array.newInstance(tmp.getClass(), numCols, numRows);
    }

    /**
     * Create a new Board (SlidingBoard, or MatchingBoard) with the given tokens, tokens.
     *
     * @param tokens The tokens that belong in the new Board
     */
    protected Board(List<T> tokens) {
        Class<?> classT = tokens.get(0).getClass();
        Iterator<T> iterator = tokens.iterator();
        T tmp = tokens.get(0);

        if (tmp instanceof Tile) {
            numRows = (int) Math.sqrt(tokens.size());
            numCols = numRows;
        } else if (tmp instanceof Card) {
            numRows = 4;
            numCols = tokens.size() / 4;
        }
        this.tokens = (T[][]) Array.newInstance(classT, numRows, numCols);

        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tokens[row][col] = iterator.next();
            }
        }
    }

    /**
     * Return the number of rows for this Board.
     *
     * @return number of rows in this Board
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Return the number of columns for this board.
     *
     * @return number of columns in this Board
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Returns an iterator for this Board.
     *
     * @return an iterator for this Board.
     */
    @NonNull
    public Iterator<T> iterator() {
        return new BoardIterator();
    }

    /**
     * An Iterator for Board Token T.
     */
    private class BoardIterator implements Iterator<T> {
        /**
         * The row location of the next Token to return.
         */
        private int curRow = 0;
        /**
         * The column location of the next Token to return.
         */
        private int curCol = 0;

        @Override
        public boolean hasNext() {
            return curRow < numRows && curCol < numCols;
        }

        /**
         * Resets column to 0 and starts a new row once a column end is reached.
         */
        private void resetIndex() {
            if (curCol == numCols) {
                curCol = 0;
                curRow++;
            }
        }

        @Override
        public T next() {
            if (!hasNext()) return null;
            T t = tokens[curRow][curCol];
            curCol++;
            resetIndex();
            return t;
        }
    }
}