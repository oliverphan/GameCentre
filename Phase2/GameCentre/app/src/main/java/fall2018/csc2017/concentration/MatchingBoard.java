package fall2018.csc2017.concentration;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The MatchingBoard tiles board.
 */
public class MatchingBoard extends Board<Card> {

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * A new MatchingBoard in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param cards the cards contained in this MatchingBoard
     */
    MatchingBoard(List<Card> cards) {
        super(cards);
        this.cards = super.tokens;
    }


//    /**
//     * Return the total amount of Cards contained on this MatchingBoard, not the amount of unique cards.
//     *
//     * @return the total amount of Cards in this MatchingBoard
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
        for (Card[] c : cards) {
            for (Card card : c) {
                if (!card.isMatched()) {
                    card.setFaceDown(true);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "MatchingBoard{" +
                "cards=" + Arrays.toString(cards) +
                "}";
    }
}
