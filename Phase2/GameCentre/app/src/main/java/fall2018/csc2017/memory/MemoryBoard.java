package fall2018.csc2017.memory;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The MemoryBoard tiles board.
 */
public class MemoryBoard extends Board<Card> {

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * A new MemoryBoard in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param cards the cards contained in this MemoryBoard
     */
    MemoryBoard(List<Card> cards) {
        super(cards);
        this.cards = super.tokens;
    }


//    /**
//     * Return the total amount of Cards contained on this MemoryBoard, not the amount of unique cards.
//     *
//     * @return the total amount of Cards in this MemoryBoard
//     */
//    int numCards() {
//        return numRows * this.numCols;
//    }

    Card getCard(int row, int col) {
        return cards[row][col];
    }

    Card[][] getCards() {
        return this.cards;
    }

//    public boolean gameFinished() {
//        for (Card[] c : cards) {
//            for (Card card : c) {
//                if (!card.isMatched()) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    /**
     * Set the Card at row, col to be face up.
     *
     * @param row the row which contains the target Card
     * @param col the column which contains the target Card
     */
    void flipCard(int row, int col) {
        getCard(row, col).setFaceDown(false);
    }

    void allFaceDown() {
        // TODO: Is this necessary???? At the end of a turn to ensure no cards are face up.
        for (Card[] c : cards) {
            for (Card card : c) {
                card.setFaceDown(true);
            }
        }
    }

    @Override
    public String toString() {
        return "MemoryBoard{" +
                "cards=" + Arrays.toString(cards) +
                "}";
    }
}
