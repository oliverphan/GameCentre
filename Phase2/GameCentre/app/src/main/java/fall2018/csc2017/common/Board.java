package fall2018.csc2017.common;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.Memory.Card;
import fall2018.csc2017.connectFour.Piece;
import fall2018.csc2017.slidingtiles.Tile;

@SuppressWarnings("unchecked")
public abstract class Board<T> extends Observable implements Serializable, Iterable<T> {
    int numRows;
    int numCols;

    // T is Card, Piece, or Tile
    private T[][] tokens;


    // For SlidingBoard, MemoryBoard
    public Board(List<T> tokens) {
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

    // For FourBoard (which only uses the null constructor)
    /**
     * Constructs a board filled of type T
     */
    public Board() {
        numCols = 7;
        numRows = 6;
        T tmp = (T) new Piece();
        this.tokens = (T[][]) Array.newInstance(tmp.getClass(), numCols, numRows);
    }

    /**
     * Returns an iterator for this board.
     *
     * @return an iterator for this board.
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
         * The index of the next Token to return.
         */
        private int curRow = 0;
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
            // Return null if next() is called when there are no other Token
            if (!hasNext()) return null;
            // Return the current next Token, prepare the next Token
            T t = tokens[curRow][curCol];
            curCol++;
            resetIndex();
            return t;
        }
    }
}