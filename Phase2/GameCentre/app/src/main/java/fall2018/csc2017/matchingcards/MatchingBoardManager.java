package fall2018.csc2017.matchingcards;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

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
    private boolean undoLeft = true;

    private Card firstCard;
    private Card secondCard;

    public Card getFirstCard() {
        return this.firstCard;
    }

    public void setFirstCard(Card c) {
        this.firstCard = c;
    }

    public Card getSecondCard() {
        return this.secondCard;
    }

    public void setSecondCard(Card c) {
        this.secondCard = c;
    }

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
        System.out.println(cards);
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
        if (undoLeft) {
            board.allFaceDown();
            this.firstCard = null;
            numMoves -= 1;
            undoLeft = false;
        }
    }

    private boolean checkCardsMatch() {
        if (!this.firstCard.equals(this.secondCard)) {
            try {
                Thread.sleep(2000);
                this.firstCard.flip();
                this.secondCard.flip();
                this.firstCard = null;
                this.secondCard = null;
            } catch (InterruptedException e) {
                Log.e("Thread Interrupted", "Failed to sleep!");
                Thread.currentThread().interrupt();
            }
            return false;
        }
        return true;
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
    protected boolean gameFinished() {
        Card[][] cards = board.getCards();
        for (Card[] c : cards) {
            for (Card card : c) {
                if (!card.isMatched()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected boolean isValidTap(int position) {
        int row = position / difficulty;
        int col = position % difficulty;
        Card toTap = board.getCard(row, col);
        return toTap.isFaceDown();
    }

    @Override
    protected void touchMove(int position) {
        int row = position / difficulty;
        int col = position % difficulty;
        numMoves += 1;
        board.flipCard(row, col);

        if (this.firstCard == null) {
            this.firstCard = board.getCard(row, col);
        } else {
            this.secondCard = board.getCard(row, col);
            if (checkCardsMatch()) {
                this.firstCard.setMatched();
                this.secondCard.setMatched();
            }
        }
    }
}