package fall2018.csc2017.connectfour;

import fall2018.csc2017.common.BoardManager;

public class FourBoardManager extends BoardManager<FourBoard> {

    /**
     * Create a FourBoardManager with the selected difficulty.
     *
     * @param difficulty the difficulty selected
     */
    public FourBoardManager(int difficulty) {
        super(difficulty);
        setName("Connect Four");
        board = new FourBoard();
    }

    /**
     * Generate the score for the current game
     * a more difficult game yields a higher score, losing subtracts a higher amount from score
     *
     * @return the score generated
     */
    @Override
    public int generateScore() {
        return board.isWinner(2)? 0 :100 * (difficulty + 1)
                - (numMoves * (4 - difficulty));
    }

    /**
     * Returns if the game is finished whether the board is full or there is a winner
     *
     * @return true if the game is finished
     */
    @Override
    protected boolean gameFinished() {
        return board.isBoardFull() || board.isWinner(1) || board.isWinner(2);
    }

    /**
     * Checks whether current tap is valid
     *
     * @param position the position tapped
     * @return boolean whether the tap is valid or not
     */
    @Override
    protected boolean isValidTap(int position) {
        int col = position % board.getNumCols();
        return board.openRow(col) > -1;
    }

    /**
     * Completes move that is selected
     *
     * @param position the position tapped
     */
    protected void touchMove(int position) {
        int col = position % board.getNumCols();
        if (board.openRow(col) > -1) {
            numMoves++;
            board.placePiece(col, board.curPlayer);
        }
    }
}
