package fall2018.csc2017.connectfour;

import android.os.Handler;

import java.util.Random;

import fall2018.csc2017.common.BoardManager;

public class FourBoardManager extends BoardManager<FourBoard> {
    /**
     * The computer player for this BoardManager.
     */
    private ComputerPlayer ai;

    /**
     * The current player in the game.
     */
    private int curPlayer;


    /**
     * Create a FourBoardManager with the selected difficulty.
     *
     * @param difficulty the difficulty selected
     */
    public FourBoardManager(int difficulty) {
        super(difficulty);
        setName("Connect Four");
        board = new FourBoard();
        curPlayer = initPlayer();
        ai = new ComputerPlayer(board, difficulty);
    }

    /**
     * Getter for current player.
     *
     * @return int representing current player
     */
    int getCurPlayer() {
        return this.curPlayer;
    }

    private int initPlayer() {
        return new Random().nextInt(2) + 1;
    }

    /**
     * Switch to the next player's turn.
     */
    private void switchPlayer() {
        if (curPlayer == 1) {
            curPlayer = 2;
            if (!gameFinished())
                makeAIMove();
        } else {
            curPlayer = 1;
        }
    }

    /**
     * Calls AI to make a move (with added sleep delay for better player experience).
     */
    void makeAIMove() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            board.placePiece(ai.getComputerMove(), 2);
            switchPlayer();
        }, 500);
    }

    /**
     * Generate the score for the current game.
     * The score is calculated by 100 * (difficulty + 1) - (numMoves * (4 - difficulty)).
     * If the player loses, they automatically get a score of 0.
     * If there is a draw, the player is given a score of 42.
     *
     * @return the score generated
     */
    @Override
    public int generateScore() {
        if (!board.isBoardFull()) {
            if (board.isWinner(2))
                return 0;
            return 100 * (difficulty + 1) - (numMoves * (4 - difficulty));
        }
        return 42;
    }

    @Override
    protected boolean gameFinished() {
        return board.isBoardFull() || board.isWinner(1) || board.isWinner(2);
    }

    @Override
    protected void touchMove(int position) {
        int col = position % board.getNumCols();
        numMoves++;
        board.placePiece(col, 1);
        switchPlayer();
    }

    @Override
    protected boolean isValidTap(int position) {
        int col = position % board.getNumCols();
        return board.openRow(col) > -1 && curPlayer == 1;
    }
}
