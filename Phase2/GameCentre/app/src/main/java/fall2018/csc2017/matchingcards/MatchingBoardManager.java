package fall2018.csc2017.matchingcards;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Handler;

import fall2018.csc2017.common.BoardManager;

/**
 * Manage a MatchingBoard, checking for win, and managing taps.
 */
public class MatchingBoardManager extends BoardManager<MatchingBoard> {

    /**
     * The number of Cards in the MatchingBoard.
     */
    private final int numCards;

    /**
     * If there is an undo move left. Each game permits one undo move.
     */
    private boolean undoUsed = false;

    /**
     * The first Card selected in a move.
     */
    private Card firstCard;

    /**
     * The second Card selected in a move.
     */
    private Card secondCard;

    /**
     * Pausing used to flag whether or not a delay is happening during checkMatched
     */
    private boolean pausing;

    /**
     * Manage a new MatchingBoard with the specified difficulty.
     *
     * @param difficulty the difficulty of this MatchingBoard
     */
    public MatchingBoardManager(int difficulty) {
        super(difficulty);
        setName("Matching Cards");
        this.numCards = 4 * difficulty;
        this.firstCard = null;
        this.secondCard = null;
        this.pausing = false;
        setDifficulty();
    }

    /**
     * Set the difficulty for this MatchingBoard, and generate a new board based on the difficulty.
     */
    private void setDifficulty() {
        List<Card> cards = new ArrayList<>();
        for (int cardNum = 0; cardNum != numCards / 2; cardNum++) {
            cards.add(new Card(cardNum));
            cards.add(new Card(cardNum));
        }
        this.board = new MatchingBoard(cards);
        shuffle(board.getCards());
    }

    /**
     * Shuffles the Cards in this MatchingBoard to random positions.
     * Adapted from Fisher-Yates algorithm.
     */
    private void shuffle(Card[][] cards) {
        Random random = new Random();

        for (int i = cards.length - 1; i > 0; i--) {
            for (int j = cards[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                Card temp = cards[i][j];
                cards[i][j] = cards[m][n];
                cards[m][n] = temp;
            }
        }
    }

    /**
     * Undo a move if there is an undo move left.
     */
    void undoMove() {
        if (!undoUsed) {
            for (int i = 0; i < board.getCards().length; i++) {
                for (int j = 0; j < board.getCards()[i].length; j++) {
                    Card checkCard = board.getCard(i, j);
                    if (!checkCard.isFaceDown() && !checkCard.isMatched() && !undoUsed()) {
                        firstCard = null;
                        board.flipCard(checkCard);
                        this.undoUsed = true;

                    }
                }
            }
        }
    }

    /**
     * Return whether or not undo has been used in this game.
     *
     * @return if the undo button has been used, or not
     */
    boolean undoUsed() {
        return this.undoUsed;
    }


    @Override
    public int generateScore() {
        int score = (100 * numCards) - (numMoves * 5);
        if (score >= 0) {
            return score;
        }
        return 0;
    }

    @Override
    protected boolean isValidTap(int position) {
        int row = position / difficulty;
        int col = position % difficulty;
        Card toTap = board.getCard(row, col);
        return toTap.isFaceDown() && !toTap.isMatched() && !pausing;
    }

    @Override
    protected void touchMove(int position) {
        int row = position / difficulty;
        int col = position % difficulty;

        if (null == firstCard) {
            firstCard = board.getCard(row, col);
            board.flipCard(firstCard);
        } else {
            secondCard = board.getCard(row, col);
            board.flipCard(secondCard);
            checkMatched();
        }
    }

    @Override
    protected boolean gameFinished() {
        for (Card[] row : board.getCards()) {
            for (Card c : row) {
                if (!c.isMatched()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check to see if two Cards are matched. If two Cards do not match,
     * there is a half second delay, then the two Cards are turned to be face down.
     */
    private void checkMatched() {
        if (firstCard.equals(secondCard)) {
            board.setCardMatched(firstCard);
            board.setCardMatched(secondCard);


            firstCard = null;
            secondCard = null;
        } else {
            pausing = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    board.flipCard(firstCard);
                    board.flipCard(secondCard);
                    firstCard = null;
                    secondCard = null;
                }
            }, 200);
            pausing = false;
        }
    }
}