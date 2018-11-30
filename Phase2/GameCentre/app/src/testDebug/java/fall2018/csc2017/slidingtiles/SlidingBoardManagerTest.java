package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.*;

public class SlidingBoardManagerTest {
    /**
     * The slidingBoardManager to be tested
     */
    private SlidingBoardManager slidingBoardManager;

    /**
     * The position of the blank tile on the board
     */
    private int blankIndex;


    /**
     * Sets up slidingBoardManager, populates the tileArray and generates blankIndex.
     */
    private void setUpBoard() {
        slidingBoardManager = new SlidingBoardManager(4);
        SlidingBoard board = slidingBoardManager.getBoard();
        ArrayList<Integer> tileArray = new ArrayList<>();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                tileArray.add(board.getTile(row, col).getId());
            }
        }
        blankIndex = tileArray.indexOf(16);
    }


    /**
     * Test whether generateScore works as intended
     */
    @Test
    public void generateScore() {
        setUpBoard();
        assertEquals(1600, slidingBoardManager.generateScore());
        if (blankIndex != 0 && blankIndex != 4 && blankIndex != 8 && blankIndex != 12) {
            slidingBoardManager.touchMove(blankIndex - 1);
            int score = 1600 - slidingBoardManager.getNumMoves();
            assertEquals(score, slidingBoardManager.generateScore());

        }

        if (blankIndex != 3 && blankIndex != 7 && blankIndex != 11 && blankIndex != 15) {
            slidingBoardManager.touchMove(blankIndex + 1);
            int score = 1600 - slidingBoardManager.getNumMoves();
            assertEquals(score, slidingBoardManager.generateScore());

        }
    }


    /**
     * Test whether isValidTap works as intended
     */
    @Test
    public void isValidTap() {
        setUpBoard();
        assertFalse(slidingBoardManager.isValidTap(blankIndex));

        if (blankIndex != 0 && blankIndex != 4 && blankIndex != 8 && blankIndex != 12) {
            assertTrue(slidingBoardManager.isValidTap(blankIndex - 1));
        }
        if (blankIndex != 3 && blankIndex != 7 && blankIndex != 11 && blankIndex != 15) {
            assertTrue(slidingBoardManager.isValidTap(blankIndex + 1));
        }


    }

    /**
     * Test whether touchMove works as intended
     */
    @Test
    public void touchMove() {
        setUpBoard();
        int row = blankIndex / 4;
        int col = blankIndex % 4;
        if (blankIndex != 0 && blankIndex != 4 && blankIndex != 8 && blankIndex != 12) {
            slidingBoardManager.touchMove(blankIndex - 1);
            assertNotEquals(16, slidingBoardManager.getBoard().getTile(row, col).getId());
        }
        if (blankIndex != 3 && blankIndex != 7 && blankIndex != 11 && blankIndex != 15) {
            slidingBoardManager.touchMove(blankIndex + 1);
            assertNotEquals(16, slidingBoardManager.getBoard().getTile(row, col).getId());
        }


    }

    /**
     * Test whether undoMove works as intended
     */
    @Test
    public void undoMove() {
        setUpBoard();
        int row = blankIndex / 4;
        int col = blankIndex % 4;
        if (blankIndex != 0 && blankIndex != 4 && blankIndex != 8 && blankIndex != 12) {
            slidingBoardManager.touchMove(blankIndex - 1);
            slidingBoardManager.undoMove(1);
            assertEquals(16, slidingBoardManager.getBoard().getTile(row, col).getId());
        }

        if (blankIndex != 3 && blankIndex != 7 && blankIndex != 11 && blankIndex != 15) {
            slidingBoardManager.touchMove(blankIndex + 1);
            slidingBoardManager.undoMove(1);
            assertEquals(16, slidingBoardManager.getBoard().getTile(row, col).getId());
        }

    }

}