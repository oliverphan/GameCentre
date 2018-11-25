package fall2018.csc2017.connectFour;

import java.io.Serializable;

public class FourBoardManager implements Serializable {
    /**
     * The SlidingBoard being managed
     */
    private FourBoard board;

    /**
     * The amount of moves the player has completed
     */
    private int numMoves;
//    Stack<Integer> previousMoves;
    /**
     * The difficulty of the current game.
     */
    private int difficulty;

    /**
     * Return the current board being managed
     *
     * @return the board being managed
     */
    FourBoard getBoard() {
        return this.board;
    }

    /**
     * Create a SlidingBoardManager with the selected difficulty
     *
     * @param d the difficulty selected
     */
    public FourBoardManager(int d) {
        difficulty = d;
        board = new FourBoard();
    }

    /**
     * Checks whether current tap is valid
     *
     * @param position the position tapped
     * @return boolean whether the tap is valid or not
     */
    boolean isValidTap(int position) {
        int col = position % board.getNumCols();
        return board.openRow(col) > -1;
    }

    /**
     * Completes move that is selected
     *
     * @param position the position tapped
     */
    void makeMove(int position) {
        int col = position % board.getNumCols();
        if (board.openRow(col) > -1) {
            numMoves++;
//            previousMoves.push(col);
            board.placePiece(col, board.curPlayer);
        }
    }

    /**
     * Return the difficulty of the board manager
     *
     * @return the difficulty
     */
    int getDifficulty() {
        return difficulty;
    }

    /**
     * Generate the score for the current game
     * a more difficult game yields a higher score, losing subtracts a higher amount from score
     *
     * @return the score generated
     */
    int generateScore() {
        return 100 * difficulty * numMoves - (board.isWinner(1) ? 0 : 10 * difficulty);
    }

    /**
     * Returns if the game is finished whether the board is full or there is a winner
     *
     * @return true if the game is finished
     */
    boolean gameFinished() {
        return board.isBoardFull() || board.isWinner(1) || board.isWinner(2);
    }
}
