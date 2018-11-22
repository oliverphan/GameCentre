package fall2018.csc2017.Memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manage a MemoryBoard, checking for win, and managing taps.
 */
public class MemoryBoardManager implements Serializable {

    /**
     * The MemoryBoard being managed.
     */
    private MemoryBoard memoryBoard;

    /**
     * the number of moves made so far.
     */
    private int numMoves;

    /**
     * The number of Cards per side of the MemoryBoard.
     */
    private int difficulty;

    /**
     * The number of Cards in the MemoryBoard.
     */
    private final int numCards;

    private static final  String name = "Memory Tiles";

    /**
     * Manage a new MemoryBoard with the specified difficulty.
     *
     * @param difficulty the difficulty of this MemoryBoard
     */
    public MemoryBoardManager(int difficulty) {
        this.numMoves = 0;
        this.difficulty = difficulty;
        this.numCards = 4 * difficulty;
        setDifficulty();
    }

    /**
     * The name of this game is "Memory".
     *
     * @return the name of this game
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the MemoryBoard being managed.
     *
     * @return the MemoryBoard
     */
    MemoryBoard getMemoryBoard() {
        return this.memoryBoard;
    }

    /**
     * Return the difficulty ogf this game.
     *
     * @return the difficulty
     */
    int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Return the amount of moves which have been made so far.
     *
     * @return how many moves have been made
     */
    int getNumMoves() {
        return this.numMoves;
    }

    /**
     * Return the final Score of this game.
     *
     * @return the final Score of the game
     */
    int generateScore() {
        int score = (100 * numCards) - (numMoves * 5);
        if (score >= 0) {
            return score;
        }
        return 0;
    }

    /**
     * Set the difficulty for this MemoryBoard, and generate a new board based on the difficulty.
     */
    private void setDifficulty() {
        List<Card> cards = new ArrayList<>();
        for (int cardNum = 0; cardNum != numCards; cardNum++) {
            Card newCard = new Card(cardNum);
            // Cards are added in pairs.
            cards.add(newCard);
            cards.add(newCard);
        }
        this.memoryBoard = new MemoryBoard(cards);
        shuffle(memoryBoard.getCards());
    }

    /**
     * Shuffles the Cards in this MemoryBoard to random positions.
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

    boolean gameOver() {
        // Return whether or not all of the tiles have been paired.
        // TODO
        return false;
    }

    /**
     * Return whether or not this Card is facing down and has not yet had it's pair located.
     *
     * @param position the position of the Card to check.
     * @return if this Card is unmatched and currently facing down
     */
    boolean isValidTap(int position) {
        int row = position / 4;
        int col = position % difficulty;
        Card toTap = memoryBoard.getCard(row, col);
        return toTap.isFaceDown() && toTap.isMatched();
    }

    /**
     * Process a touch at position on the MemoryBoard, turning over the Card as appropriate.
     *
     * @param position the position of the touch on the board
     */
    void touchMove(int position) {
        int row = position / 4;
        int col = position / difficulty;
        numMoves += 1;
        memoryBoard.flipCard(row, col);
    }
}
