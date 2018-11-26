package fall2018.csc2017.scoring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.common.ScoreDisplay;

public class LeaderBoard implements Serializable, ScoreDisplay {

    /**
     * A HashMap representing the games and their top three scores.
     * Key: The name of the game
     * Value: The top three scores
     */
    private Map<String, ArrayList<Score>> gameScores;

    /**
     * The number of names and scores to show up on the leaderboard.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final int NUM_LEADERBOARD_SLOTS = 5;

    public LeaderBoard() {
        this.gameScores = new HashMap<>();
        this.gameScores.put("Sliding Tiles", new ArrayList<>());
        this.gameScores.put("Connect Four", new ArrayList<>());
        this.gameScores.put("Matching Cards", new ArrayList<>());
    }

    /**
     * Returns a string containing the top scores of this game.
     *
     * @param gameName the game whose scores should be returned
     * @return An ArrayList of the top three scores of this game
     */
    public ArrayList<Score> getTopScores(String gameName) {
        return gameScores.get(gameName);
    }

    /**
     * Looks in the given game's current high scores to see if this score is higher than an
     * existing one, if it is, the LeaderBoard is updated with the user's name and the score.
     *
     * @param gameName the game to have its LeaderBoard checked
     * @param newScore the Score object to be compared with the existing scores
     */
    public void updateScores(String gameName, Score newScore) {
        // topScores is an ArrayList of the top 5 score objects for this game.
        ArrayList<Score> topScores = this.gameScores.get(gameName);
        for (int i = 0; i < NUM_LEADERBOARD_SLOTS && i < topScores.size(); i++) {
            if (newScore.getValue() > topScores.get(i).getValue()) {
                topScores.add(i, newScore);
                topScores.remove(topScores.size() - 1);
                break;
            }
        }
    }
}
