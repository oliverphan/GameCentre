package fall2018.csc2017.scoring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeaderBoardTest {

    /**
     * A LeaderBoard for testing
     */
    LeaderBoard testLeaderBoard;

    /**
     * A List of scores used as 'expected'.
     */
    List<Score> expectedScores;

    /**
     * Makes the LeaderBoard for testing, and the expectedScores for Sliding Tiles.
     */
    private void buildBoard() {
        this.testLeaderBoard = new LeaderBoard();
        this.expectedScores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expectedScores.add(new Score("", 0));
        }
    }

    /**
     * Tests the getGameScores() method with no updated scores..
     */
    @Test
    public void testGetGameScores() {
        buildBoard();
        assertTrue(checkElements(expectedScores));
    }

    /**
     * Tests the updateScores() method.
     */
    @Test
    public void testUpdateScores() {
        buildBoard();
        Score toAdd = new Score("User1", 100);
        expectedScores.set(0, toAdd);
        testLeaderBoard.updateScores("Sliding Tiles", toAdd);
        assertTrue(checkElements(expectedScores));
    }

    /**
     * Tests the updateLeaderBoard() method.
     */
    @Test
    public void testUpdateFullLeaderBoard() {
        testLeaderBoard = new LeaderBoard();
        buildBoard();
        Score score1 = new Score("User", 100);
        Score score2 = new Score("User", 200);
        Score score3 = new Score("User", 150);

        testLeaderBoard.updateScores("Sliding Tiles", score1);
        expectedScores.set(0, score1);
        assertTrue(checkElements(testLeaderBoard.getGameScores("Sliding Tiles")));

        testLeaderBoard.updateScores("Sliding Tiles", score2);
        expectedScores.set(0, score2);
        expectedScores.set(1, score1);
        assertTrue(checkElements(testLeaderBoard.getGameScores("Sliding Tiles")));

        testLeaderBoard.updateScores("Sliding Tiles", score3);
        expectedScores.set(1, score3);
        expectedScores.set(2, score1);
        assertTrue(checkElements(testLeaderBoard.getGameScores("Sliding Tiles")));
    }

    /**
     * A helper method for checking all elements in a Score array.
     */
    private boolean checkElements(List<Score> toCompare) {
        for (Score s : this.testLeaderBoard.getGameScores("Sliding Tiles")) {
            int i = this.testLeaderBoard.getGameScores("Sliding Tiles").indexOf(s);
            if (!toCompare.get(i).equals(s)) {
                return false;
            }
        }
        return true;
    }
}