package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SlidingBoardTest {

    /**
     * A 4 X 4 SlidingBoard to test.
     */
    private SlidingBoard testSlidingBoard;

    /**
     * A List of Tiles for testing.
     */
    private List<Tile> tiles;

    /**
     * Makes a SlidingBoard for testing.
     */
    private void makeSlidingBoard() {
        makeTiles();
        testSlidingBoard = new SlidingBoard(tiles);
    }

    /**
     * Makes Tiles for testing
     */
    private void makeTiles() {
        tiles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            tiles.add(new Tile(i, i));
        }
    }
    /**
     * Tests the numTiles() method.
     */
    @Test
    public void testNumTiles() {
        makeSlidingBoard();
        assertEquals(16, testSlidingBoard.numTiles());
    }

    /**
     * Tests the getTile() method.
     */
    @Test
    public void testGetTile() {
        makeSlidingBoard();
        Tile expected = new Tile(0, 0);
        assertEquals(0, expected.compareTo(testSlidingBoard.getTile(0, 0)));
    }

    /**
     * Tests the swapTiles() method.
     */
    @Test
    public void testSwapTiles() {
        makeSlidingBoard();
        Tile tile1 = tiles.get(0);
        Tile tile2 = tiles.get(1);
        tiles.set(0, tile2);
        tiles.set(1, tile1);
        testSlidingBoard.swapTiles(0, 0, 0, 1);
        assertEquals(0, tiles.get(0).compareTo(testSlidingBoard.getTile(0, 0)));
        assertEquals(0, tiles.get(1).compareTo(testSlidingBoard.getTile(0, 1)));
    }
}