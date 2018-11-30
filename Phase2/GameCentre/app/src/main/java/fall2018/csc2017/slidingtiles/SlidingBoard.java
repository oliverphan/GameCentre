package fall2018.csc2017.slidingtiles;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The sliding tiles board.
 */
public class SlidingBoard extends Board<Tile> {

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
        super(tiles);
        this.tiles = super.tokens;
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
}
