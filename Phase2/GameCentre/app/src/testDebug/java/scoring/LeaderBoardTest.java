package scoring;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class LeaderBoardTest {

    @Test
    public void getTopScoresNoGame() {
        LeaderBoard leaderBoardNoGame = new LeaderBoard();
        final ArrayList<Score> expected = new ArrayList<>(); // an array of scores (empty)
        final ArrayList<Score> actual = leaderBoardNoGame.getTopScores("Non-existent Game");
        assertEquals(expected, actual);
    }

    @Test
    public void getTopScoresOneGame() {
        final ArrayList<Score> expected = new ArrayList<>();
        final Score score1 = new Score("Only User", 1);
        expected.add(score1);
        LeaderBoard leaderBoardOneGame = new LeaderBoard();
        leaderBoardOneGame.updateScores("Only Game", score1);
        final ArrayList<Score> actual = leaderBoardOneGame.getTopScores("Only Game");
        assertEquals(expected, actual);
    }

    @Test
    public void getTopScoresTwoGames() {
        LeaderBoard leaderBoardTwoGames = new LeaderBoard();
        final ArrayList<Score> expected = new ArrayList<>();
        final Score scoreGame1 = new Score("User A", 1);
        final Score scoreGame2 = new Score("User A", 2);
        leaderBoardTwoGames.updateScores("First Game", scoreGame1);
        leaderBoardTwoGames.updateScores("Second Game", scoreGame2);
        expected.add(scoreGame2);
        final ArrayList<Score> actual = leaderBoardTwoGames.getTopScores("Second Game");
        assertEquals(expected, actual);
    }

    @Test
    public void updateScoresGameDNE() {
        LeaderBoard actual = new LeaderBoard();
        HashMap<String, ArrayList<Score>> expected = new HashMap<>();
        Score score1 = new Score("User A", 1);
        ArrayList<Score> scores = new ArrayList<>();
        scores.add(score1);
        expected.put("Only Game", scores);
        actual.updateScores("Only Game", score1);
        assertEquals(expected, actual.getGameScores());
    }

    @Test
    public void updateScoreExistingGame() {
        // TODO Update existing Game
    }

    @Test
    public void updateScoreExistingGameLowerScore() {
        // TODO Update with lower Score
    }

}
