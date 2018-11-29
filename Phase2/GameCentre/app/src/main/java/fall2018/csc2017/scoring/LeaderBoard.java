package fall2018.csc2017.scoring;

import java.io.Serializable;
import java.lang.reflect.Array;
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
     * The number of names and scores to show up on the LeaderBoard.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final int NUM_LEADERBOARD_SLOTS = 5;

    public LeaderBoard() {
        this.gameScores = new HashMap<>();
        this.gameScores.put("Sliding Tiles", initialList());
        this.gameScores.put("Connect Four", initialList());
        this.gameScores.put("Matching Cards", initialList());
    }

    private ArrayList<Score> initialList(){
        ArrayList<Score> list = new ArrayList<>();
        for (int i = 0; i < NUM_LEADERBOARD_SLOTS; i++){
            list.add(new Score("", 0));
        }
        return list;
    }
    /**
     * Returns a string containing the top scores of this game.
     *
     * @param gameName the game whose scores should be returned
     * @return An ArrayList of the top five scores of this game
     */
    public ArrayList<Score> getGameScores(String gameName) {
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
        ArrayList<Score> scores = getGameScores(gameName);
        for (int i = 0; i < NUM_LEADERBOARD_SLOTS; i++) {
            int scoreValue = scores.get(i).getValue();
            if (newScore.getValue() > scoreValue) {
                scores.add(i, newScore);
                scores.remove(scores.size() - 1);
                return;
            }
        }
    }
}
