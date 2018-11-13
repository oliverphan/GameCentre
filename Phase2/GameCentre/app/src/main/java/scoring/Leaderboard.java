package scoring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Leaderboard implements Serializable {

    /**
     * A HashMap representing the games and their top three scores.
     * Key: The name of the game
     * Value: The top three scores
     */
    private HashMap<String, ArrayList<Score>> gameScores;

    Leaderboard() {
        this.gameScores = new HashMap<>();
        ArrayList<Score> temp = new ArrayList<>();
        Score s = new Score("", 0);
        temp.add(0, s);
        temp.add(1, s);
        temp.add(2, s);
        this.gameScores.put("Sliding Tiles", temp);
    }

    /**
     * Returns a string containing the top scores of this game.
     *
     * @param gameName the game whose scores should be returned
     * @return An ArrayList of the top three scores of this game
     */
    ArrayList<Score> getTopScores(String gameName) {
        return gameScores.get(gameName);
    }

    /**
     * Looks in the given game's current high scores to see if this score is higher than an
     * existing one, if it is, the Leaderboard is updated with the user's name and the score.
     *
     * @param gameName the game to have its Leaderboard checked
     * @param newScore the Score object to be compared with the existing scores
     */
    void updateScores(String gameName, Score newScore) {
        // topThree is an an array list of the top three score objects for this game
        ArrayList<Score> topThree = this.gameScores.get(gameName);

        Collections.sort(topThree, Collections.reverseOrder(new ScoreComparator()));

        for (int i = 0; i < topThree.size(); i++) {
            if (newScore.getValue() > topThree.get(i).getValue()) {
                topThree.add(i, newScore);
                topThree.remove(topThree.size() - 1);
                break;
            }
        }
    }
}
