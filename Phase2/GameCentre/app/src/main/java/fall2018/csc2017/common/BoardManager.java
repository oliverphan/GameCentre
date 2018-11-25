package fall2018.csc2017.common;

import java.io.Serializable;

public abstract class BoardManager<T> implements Serializable {

    protected T board;
    protected int difficulty;

    /**
     * The number of moves made so far.
     */
    protected int numMoves;

    /**
     *
     */
    private String name;

    /**
     * Constructor for standard board manager.
     *
     * @param difficulty the difficulty for the game
     */
    public BoardManager(int difficulty) {
        this.difficulty = difficulty;
        this.numMoves = 0;
    }

    /**
     * Getter for the name of game.
     *
     * @return the name of the game
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of game.
     *
     * @param s the name of the game
     */
    protected void setName(String s) {
        name = s;
    }

    /**
     * Return the current board being managed.
     *
     * @return the board
     */
    public T getBoard() {
        return board;
    }

    /**
     * Return the difficulty of the game.
     *
     * @return the difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Return the number of moves made so far.
     *
     * @return the number of moves
     */
    public int getNumMoves() {
        return numMoves;
    }

    public abstract int generateScore();

    protected abstract boolean gameFinished();

    protected abstract boolean isValidTap(int position);

    protected abstract void touchMove(int position);
}
