package fall2018.csc2017.scoring;

import java.io.Serializable;


public class Score implements Serializable {

    /**
     * The name of the user that got this Score.
     */
    private String username;

    /**
     * The value of the Score.
     */
    private int value;

    /**
     * Initialize a new Score object.
     *
     * @param username the name of the user that got this Score
     * @param value    the value of this score
     */
    public Score(String username, int value) {
        this.username = username;
        this.value = value;
    }

    /**
     * Return the name of the User who Scored this Score.
     *
     * @return This Scores Users username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the score value of this Score.
     *
     * @return the integer value that a User Scored
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Updates value based on input
     *
     * @param newScore new value being input
     */
    public void setValue(int newScore) {
        this.value = newScore;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Score)) {
            return false;
        }
        Score obj = (Score) o;
        return (obj.value == this.value && obj.username.equals(this.username));
    }

    @Override
    public String toString() {
        return this.username + " : " + this.value;
    }
}
