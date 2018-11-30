package fall2018.csc2017.slidingtiles;

import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The Sliding Tiles board.
 */
public class SlidingBoard extends Board<Tile> {

    /**
     * The Tiles on the Board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new Board of Tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the Tiles for the Board
     */
    SlidingBoard(List<Tile> tiles) {
        super(tiles);
        this.tiles = super.tokens;
    }

    /**
     * Return the Tile at (row, col).
     *
     * @param row the Tile row
     * @param col the Tile column
     * @return the Tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the number of Tiles on the Board.
     *
     * @return the number of Tiles on the Board
     */
    int numTiles() {
        return numRows * numCols;
    }

    /**
     * Swap the Tiles at (row1, col1) and (row2, col2).
     *
     * @param row1 the first Tile row
     * @param col1 the first Tile col
     * @param row2 the second Tile row
     * @param col2 the second Tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile temp1 = getTile(row1, col1);
        Tile temp2 = getTile(row2, col2);
        tiles[row1][col1] = temp2;
        tiles[row2][col2] = temp1;
        setChanged();
        notifyObservers();
    }
}
