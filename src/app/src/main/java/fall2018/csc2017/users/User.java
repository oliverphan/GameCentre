package fall2018.csc2017.users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    /**
     * The username of this User.
     */
    private String username;

    /**
     * The password for this User.
     */
    private String password;

    /**
     * A HashMap for storing all of this Users scores, with key gameName, and value scoreValue.
     */
    private Map<String, Integer> scores;

    /**
     * A HashMap  for storing all of this Users saves, with key gameName, and value being
     * a BoardManager for a game state.
     */
    private Map<String, Object> saves;

    /**
     * Constructor for a non-guest User. Which is added into the GameLaunchCentre.
     *
     * @param username the username for User
     * @param password the password for User
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.scores = new HashMap<>();
        initializeScores();
        this.saves = new HashMap<>();
    }

    /**
     * Returns the username of this User.
     *
     * @return this Users username value
     */
    public String getName() {
        return this.username;
    }

    private void initializeScores() {
        scores.put("Sliding Tiles", 0);
        scores.put("Connect Four", 0);
        scores.put("Matching Cards", 0);
    }

    /**
     * Return the password of the User.
     *
     * @return hashed password String.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Return the Scores for this User.
     *
     * @return aHashMap of scores for all games which this User has played
     */
    public Map<String, Integer> getScores() {
        return scores;
    }

    /**
     * Saves a Users score for a the game game, if the score score is higher than an existing
     * score, the old one is replaced. If the User has not played that game before, the score
     * and game are added into scores.
     *
     * @param game  the name of the game to update the score of
     * @param score the value of the score earned in game
     */
    public void setNewScore(String game, int score) {
        if (scores.containsKey(game)) {
            if (scores.get(game) < score) {
                scores.put(game, score);
            }
        } else {
            scores.put(game, score);
        }
    }

    /**
     * Return the saves for this user, in game name, score pairs.
     *
     * @return all of the saves for every game that this User has played.
     */
    public Map<String, Object> getSaves() {
        return saves;
    }

    /**
     * Delete the save for the indicated game.
     *
     * @param game the name of the game to delete a save of
     */
    public void deleteSave(String game) {
        saves.remove(game);
    }

    /**
     * Store the BoardManager of a game in this Users save file.
     *
     * @param game         the name of the game to write a save for
     * @param boardManager the BoardManager to be saved
     */
    public void writeGame(String game, Object boardManager) {
        saves.put(game, boardManager);
    }
}