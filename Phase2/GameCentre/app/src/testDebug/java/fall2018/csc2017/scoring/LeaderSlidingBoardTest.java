package fall2018.csc2017.scoring;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeaderSlidingBoardTest {

    /**
     * The LeaderBoard for testing
     */
    private LeaderBoard testLeaderBoard;

    /**
     * A set of Scores to be used as 'expected'
     */
    private ArrayList<Score> testScores;

    /**
     * Create the test scores as a control for testing.
     */
    private void buildTestScores() {
        Score testScore = new Score("", 0);
        this.testScores = new ArrayList<>();
        this.testScores.add(0, testScore);
        this.testScores.add(1, testScore);
        this.testScores.add(2, testScore);
        this.testScores.add(3, testScore);
        this.testScores.add(4, testScore);
    }

    /**
     * Tests the getTopScores() method.
     */
    @Test
    public void testGetTopScores() {
        testLeaderBoard = new LeaderBoard();
        buildTestScores();
        assertEquals(this.testScores, testLeaderBoard.getGameScores("Sliding Tiles"));
    }

    /**
     * Tests the updateScores() method.
     */
    @Test
    public void testUpdateScores() {
        testLeaderBoard = new LeaderBoard();
        buildTestScores();
        Score topScore = new Score("Username", 100);
        this.testScores.set(0, topScore);
        testLeaderBoard.updateScores("Sliding Tiles", topScore);
        assertEquals(testScores, testLeaderBoard.getGameScores("Sliding Tiles"));
    }

    /**
     * Tests the updateLeaderBoard() method.
     */
    @Test
    public void testUpdateFullLeaderBoard() {
        testLeaderBoard = new LeaderBoard();
        buildTestScores();
        Score score1 = new Score("User", 100);
        Score score2 = new Score("User", 200);
        Score score3 = new Score("User", 150);

        testLeaderBoard.updateScores("Sliding Tiles", score1);
        testScores.set(0, score1);
        assertTrue(checkElements(testLeaderBoard.getGameScores("Sliding Tiles")));

        testLeaderBoard.updateScores("Sliding Tiles", score2);
        testScores.set(0, score2);
        testScores.set(1, score1);
        assertTrue(checkElements(testLeaderBoard.getGameScores("Sliding Tiles")));

        testLeaderBoard.updateScores("Sliding Tiles", score3);
        testScores.set(1, score3);
        testScores.set(2, score1);
        assertTrue(checkElements(testLeaderBoard.getGameScores("Sliding Tiles")));
    }

    /**
     * A helper method for checking all elements in a Score array.
     */
    private boolean checkElements(List<Score> toCompare) {
        for (Score s : this.testScores) {
            int i = this.testScores.indexOf(s);
            if (!toCompare.get(i).equals(s)) {
                return false;
            }
        }
        return true;
    }
}

