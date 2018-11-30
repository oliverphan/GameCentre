package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    /**
     * A Tile used for testing.
     */
    private Tile testTile;

    /**
     * Make the Tile for testing.
     */
    private void makeTile() {
        this.testTile = new Tile(1, 1);
    }

    /**
     * Test the getUserImage() method.
     */
    @Test
    public void testGetUserImage() {
    }

    /**
     * Test the getId() method.
     */
    @Test
    public void testGetId() {
        makeTile();
        assertEquals(2, testTile.getId());
    }

    /**
     * test the createUserTiles() method.
     */
    @Test
    public void testCreateUserTiles() {
    }

    /**
     * Test the compareTo() method with equal Tiles.
     */
    @Test
    public void testCompareTo() {
        makeTile();
        Tile compareTile = new Tile(1, 1);
        assertEquals(0, testTile.compareTo(compareTile));
    }

    /**
     * Test the compareTo() method with unequal Tiles.
     */
    @Test
    public void testCompareToUnequal() {
        makeTile();
        Tile compareTile = new Tile(5, 5);
        assertTrue(testTile.compareTo(compareTile) > 0);
    }

    /**
     * Test the compareTo() method with unequal Tiles.
     */
    @Test
    public void testCompareToUnequalReverse() {
        makeTile();
        Tile compareTile = new Tile(5, 5);
        assertTrue(compareTile.compareTo(testTile) < 0);
    }
}