package fall2018.csc2017.matchingcards;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchingBoardManagerTest {

    /**
     * A MatchingBoardManager for testing purpose.
     */
    private MatchingBoardManager testMBM;

    private void makeMBM() {
        this.testMBM = new MatchingBoardManager(4);
    }

    /**
     * Test whether the undoMove flips a card back face down.
     */
    @Test
    public void undoMove() {
        makeMBM();
        MatchingBoard board = testMBM.getBoard();
        Card[][] cardsInBoard = board.getCards();
        board.flipCard(cardsInBoard[0][0]);
        assertFalse(cardsInBoard[0][0].isFaceDown());
        testMBM.undoMove();
        assertTrue(cardsInBoard[0][0].isFaceDown());
    }

    /**
     * Test whether the undo is used successfully.
     */
    @Test
    public void undoUsed() {
        makeMBM();
        MatchingBoard board = testMBM.getBoard();
        Card[][] cardsInBoard = board.getCards();
        board.flipCard(cardsInBoard[0][0]);
        assertFalse(testMBM.undoUsed());
        testMBM.undoMove();
        assertTrue(testMBM.undoUsed());

    }

    @Test
    public void testBaseGenerateScore() {
        makeMBM();
        int curScore = testMBM.generateScore();
        // After no moves, score is (100 * 16)
        assertEquals(curScore, 1600);
        // If moves are made, Handler needs to be mocked.
    }

    /**
     * Test whether isValidTap works. Valid if card is face down, invalid id card face up.
     */
    @Test
    public void testIsValidTap() {
        makeMBM();
        MatchingBoard board = testMBM.getBoard();
        Card[][] cardsInBoard = board.getCards();

        for (int i = 0; i < cardsInBoard.length; i++) {
            assertTrue(testMBM.isValidTap(i));
        }

        for (int i = 0; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumCols(); j++) {
                board.flipCard(cardsInBoard[i][j]);
            }
        }

        for (int i = 0; i < cardsInBoard.length; i++) {
            assertFalse(testMBM.isValidTap(i));
        }
    }

    /**
     * Test the touch move to flip a card.
     */
    @Test
    public void touchMove() {
        makeMBM();
        MatchingBoard board = testMBM.getBoard();
        Card[][] cardsInBoard = board.getCards();
        testMBM.touchMove(3);
        assertFalse(cardsInBoard[0][3].isFaceDown());
    }

    /**
     * Test the game is not finished when MatchingBoardManager instantiated, and when
     * all cards matched the game is finished.
     */
    @Test
    public void gameFinished() {
        makeMBM();
        MatchingBoard board = testMBM.getBoard();
        Card[][] cardsInBoard = board.getCards();
        assertFalse(testMBM.gameFinished());
        for (Card[] row : cardsInBoard) {
            for (Card c : row) {
                c.setMatched();
            }
        }
        assertTrue(testMBM.gameFinished());
    }
}