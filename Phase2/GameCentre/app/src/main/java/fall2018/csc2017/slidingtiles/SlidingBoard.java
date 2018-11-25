package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class SlidingBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    int numRows;


    /**
     * The number of columns.
     */
    int numCols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    SlidingBoard(List<Tile> tiles) {
        numRows = (int) Math.sqrt(tiles.size());
        numCols = numRows;
        Iterator<Tile> iter = tiles.iterator();
        this.tiles = new Tile[numRows][numCols];
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return numRows * numCols;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile temp1 = getTile(row1, col1);
        Tile temp2 = getTile(row2, col2);
        tiles[row1][col1] = temp2;
        tiles[row2][col2] = temp1;
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "SlidingBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Returns an iterator for this board.
     *
     * @return an iterator for this board.
     */
    @NonNull
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * An Iterator for SlidingBoard Tiles.
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The index of the next Tile to return.
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
        public Tile next() {
            // Return null if next() is called when there are no other tiles
            if (!hasNext()) return null;
            // Return the current next Tile, prepare the next Tile
            Tile t = tiles[curRow][curCol];
            curCol++;
            resetIndex();
            return t;
        }
    }
}
