package fall2018.csc2017.Memory;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The MemoryBoard tiles board.
 */
public class MemoryBoard extends Observable implements Serializable, Iterable<Card> {

    /**
     * The number of rows.
     */
    private int numRows;


    /**
     * The number of columns.
     */
    private int numCols;

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * A new MemoryBoard in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param cards the cards contained in this MemoryBoard
     */
    MemoryBoard(List<Card> cards) {
        numRows = (int) Math.sqrt(cards.size());
        numCols = numRows;
        Iterator<Card> iterator = cards.iterator();
        this.cards = new Card[numRows][numCols];
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.cards[row][col] = iterator.next();
            }
        }
    }

    /**
     * Return the total amount of Cards contained on this MemoryBoard, not the amount of unique cards.
     *
     * @return the total amount of Cards in this MemoryBoard
     */
    int numCards() {
        return this.numRows * this.numCols;
    }

    /**
     * Return the number of unique Cards which are contained on this MemoryBoard.
     *
     * @return the amount of unique Cards in this MemoryBoard
     */
    int numUniqueCards() {
        return this.numCards() / 2;
    }

    Card getCard(int row, int col) {
        return cards[row][col];
    }

    @Override
    public String toString() {
        return "MemoryBoard{" +
                "cards=" + Arrays.toString(cards) +
                "}";
    }

    /**
     * Returns an iterator for this MemoryBoard.
     *
     * @return an iterator for this MemoryBoard
     */
    @NonNull
    public Iterator<Card> iterator() {
        return new MemoryBoardIterator();
    }

    /**
     * An Iterator for MemoryBoard Cards.
     */
    private class MemoryBoardIterator implements Iterator<Card> {
        /**
         * The index of the next Card to return.
         */
        private int curRow = 0;
        private int curCol = 0;

        @Override
        public boolean hasNext() {
            return curRow < numRows && curCol < numCols;
        }

        /**
         * Resets column to 0 and starts a new row once an end is reached.
         */
        private void resetIndex() {
            if (curCol == numCols) {
                curCol = 0;
                curRow++;
            }
        }

        @Override
        public Card next() {
            // Return null if next() is called when there are no other cards
            if (!hasNext()) return null;
            // Return the current next Card, prepare the next Card
            Card t = cards[curRow][curCol];
            curCol++;
            resetIndex();
            return t;
        }
    }
}
