package fall2018.csc2017.connectfour;

import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    /**
     * A Piece to test.
     */
    private Piece testPiece;

    /**
     * Make a Piece to test.
     */
    private void makepiece() {
        this.testPiece = new Piece();
    }

    @Test
    public void getPlayerAs0() {
        makepiece();
        assertEquals(testPiece.getPlayer(), 0);
    }

    /**
     * Test setting player to either 1 (player) or 2 (computer).
     */
    @Test
    public void setPlayer() {
        makepiece();
        this.testPiece.setPlayer(0);
        assertEquals(testPiece.getPlayer(), 0);
        this.testPiece.setPlayer(1);
        assertEquals(testPiece.getPlayer(), 1);
        this.testPiece.setPlayer(2);
        assertEquals(testPiece.getPlayer(), 2);
    }
}