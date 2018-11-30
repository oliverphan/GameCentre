package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardAndTileTest {

    /**
     * The board manager for testing.
     */
    private SlidingBoardManager slidingBoardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = slidingBoardManager.getBoard().getNumRows() * slidingBoardManager.getBoard().getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }
        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        SlidingBoard board = new SlidingBoard(tiles);
        slidingBoardManager = new SlidingBoardManager(board.getNumRows());
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingBoardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(!slidingBoardManager.gameFinished());
        swapFirstTwoTiles();
        assertFalse(!slidingBoardManager.gameFinished());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, slidingBoardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingBoardManager.getBoard().getTile(0, 1).getId());
        slidingBoardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingBoardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingBoardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, slidingBoardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, slidingBoardManager.getBoard().getTile(3, 3).getId());
        slidingBoardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, slidingBoardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, slidingBoardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(slidingBoardManager.isValidTap(11));
        assertTrue(slidingBoardManager.isValidTap(14));
        assertFalse(slidingBoardManager.isValidTap(10));
    }
}