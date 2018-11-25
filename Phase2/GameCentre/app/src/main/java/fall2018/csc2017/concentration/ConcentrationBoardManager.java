package fall2018.csc2017.concentration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fall2018.csc2017.common.BoardManager;

/**
 * Manage a ConcentrationBoard, checking for win, and managing taps.
 */
public class ConcentrationBoardManager extends BoardManager<ConcentrationBoard> {

    /**
     * The number of Cards in the ConcentrationBoard.
     */
    private final int numCards;

    /**
     * If there is an undo move left. Each game permits one undo move.
     */
    private boolean undoLeft = true;

    /**
     * Manage a new ConcentrationBoard with the specified difficulty.
     *
     * @param difficulty the difficulty of this ConcentrationBoard
     */
    public ConcentrationBoardManager(int difficulty) {
        super(difficulty);
        setName("Concentration");
        this.numCards = 4 * difficulty;
        setDifficulty();
    }

    /**
     * Return the final score of this game.
     *
     * @return the final score of the game
     */
    @Override
    public int generateScore() {
        int score = (100 * numCards) - (numMoves * 5);
        if (score >= 0) {
            return score;
        }
        return 0;
    }

    /**
     * @return whether the game has finished or not
     */
    @Override
    protected boolean gameFinished() {
        // TODO: Return whether or not all of the tiles have been paired.
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

    /**
     * Return whether or not this Card is facing down and has not yet had it's pair located.
     *
     * @param position the position of the Card to check.
     * @return if this Card is unmatched and currently facing down
     */
    @Override
    protected boolean isValidTap(int position) {
        int row = position / 4;
        int col = position % difficulty;
        Card toTap = board.getCard(row, col);
        return toTap.isFaceDown() && toTap.isMatched();
    }

    /**
     * Process a touch at position on the ConcentrationBoard, turning over the Card as appropriate.
     *
     * @param position the position of the touch on the board
     */
    @Override
    protected void touchMove(int position) {
        int row = position / 4;
        int col = position / difficulty;
        numMoves += 1;
        board.flipCard(row, col);
    }

    /**
     * Set the difficulty for this ConcentrationBoard, and generate a new board based on the difficulty.
     */
    private void setDifficulty() {
        List<Card> cards = new ArrayList<>();
        for (int cardNum = 0; cardNum != numCards; cardNum++) {
            Card newCard = new Card(cardNum);
            // Cards are added in pairs.
            cards.add(newCard);
            cards.add(newCard);
        }
        this.board = new ConcentrationBoard(cards);
        shuffle(board.getCards());
    }

    /**
     * Shuffles the Cards in this ConcentrationBoard to random positions.
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
    private void undo(int position) {
        int row = position / 4;
        int col = position / difficulty;
        int count = 0;
        if (undoLeft) {
            Card[][] cards = board.getCards();
            for (Card[] c : cards) {
                for (Card i : c) {
                    if (!(i.isFaceDown())) {
                        count++;
                    }
                }
            }
        }
        if (count == 1) {
            board.getCard(row, col).setFaceDown(true);
            numMoves -= 1;
            undoLeft = false;
        }
    }

}