package fall2018.csc2017.common;

import java.io.Serializable;

public abstract class BoardManager implements Serializable {

    private Board board;
    private int difficulty;
    private int numMoves;
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
     * Return the board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return the difficulty.
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
    int getNumMoves() {
        return numMoves;
    }

    abstract boolean isValidTap(int position);
    abstract int generateScore();
    abstract void touchMove(int position);
    abstract boolean gameFinished();
}
