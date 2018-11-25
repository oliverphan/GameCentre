package fall2018.csc2017.scoring;

import java.io.Serializable;


public class Score implements Serializable {
    private String username;
    private int value;

    public Score(String username, int value) {
        this.username = username;
        this.value = value;
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
     * Return the name of the User who Scored this Score.
     *
     * @return This Scores Users username
     */
    String getUsername() {
        return this.username;
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
    public String toString() {
        return this.username + ": " + this.value;
    }
}