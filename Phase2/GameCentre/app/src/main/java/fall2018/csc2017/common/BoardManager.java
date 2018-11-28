package fall2018.csc2017.common;

import java.io.Serializable;

public abstract class BoardManager<T> implements Serializable {


    /**
     * The Board being Managed.
     */
    protected T board;

    /**
     * The difficulty of the game.
     */
    protected int difficulty;

    /**
     * The number of moves made so far.
     */
    protected int numMoves;

    /**
     * The name of this game.
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
     * @return this board
     */
    public T getBoard() {
        return board;
    }

    /**
     * Return the difficulty of the game.
     *
     * @return the difficulty of this game
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Return the number of moves made so far.
     *
     * @return the number of moves made so far
     */
    public int getNumMoves() {
        return numMoves;
    }

    /**
     * Generate a Score specific to the game being played.
     *
     * @return the score for the current game
     */
    public abstract int generateScore();

    /**
     * Return whether or not this game is finished, or won.
     *
     * @return if this game is finished (win or lose)
     */
    protected abstract boolean gameFinished();

    /**
     * Return whether or not a tap on the display is valid:
     * there is a move available in position.
     *
     * @param position the location where the display was touched
     * @return if the position corresponds with a valid move
     */
    protected abstract boolean isValidTap(int position);

    /**
     * Make a move at position.
     *
     * @param position where to make a move
     */
    protected abstract void touchMove(int position);
}
