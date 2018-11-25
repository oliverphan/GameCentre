package fall2018.csc2017.users;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    /**
     * The username of this user.
     */
    private String username;

    /**
     * The password for this user.
     */
    private String password;

    /**
     * A hashmap storing all the scores for this user with key gameName and value scoreValue
     */
    private HashMap<String, Integer> scores;

    /**
     * A hashmap storing all the saves for this user with key gameName and value (the save file).
     */
    private HashMap<String, Object> saves;

    /**
     * Constructor for a non-guest User. Which adds itself to the GameLaunchCentre.
     *
     * @param username username for User.
     * @param password password for User that is immediately hashed for security.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.scores = new HashMap<>();
//        String temp = "Sliding Tiles";
//        scores.put(temp, 0);
        this.saves = new HashMap<>();
    }

    /**
     * Return the username of the User.
     *
     * @return username String
     */
    public String getName() {
        return this.username;
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
     * Return the HashMap of scores for this user.
     *
     * @return HashMap of scores.
     */
    public HashMap<String, Integer> getScores() {
        return scores;
    }

    /**
     * Saves user score if no score exists or new score is higher than previous.
     *
     * @param game game name
     * @param score new score passed in
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
     * Return the HashMap of saves for this user.
     *
     * @return HashMap of saves.
     */
    public HashMap<String, Object> getSaves() {
        return saves;
    }

    /**
     * Delete the save for the indicated game.
     *
     * @param game game name
     */
    public void deleteSave(String game) {
        saves.remove(game);
    }

    /**
     * Store the board manager for the indicated game.
     *
     * @param game game name
     * @param boardManager the board manager to save to this user
     */
    public void writeGame(String game, Object boardManager) {
        saves.put(game, boardManager);
    }
}