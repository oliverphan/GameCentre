package fall2018.csc2017.matchingcards;

import java.util.Arrays;
import java.util.Iterator;
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
        this.cards = new Card[4][3];
        Iterator<Card> iterator = cards.iterator();
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                Card next = iterator.next();
                System.out.println(next);
                this.cards[row][col] = next;
            }
        }

    }

    Card getCard(int row, int col) {
        return cards[row][col];
    }

    Card[][] getCards() {
        return this.cards;
    }

    /**
     * Set the Card at row, col to be face up.
     *
     * @param row the row which contains the target Card
     * @param col the column which contains the target Card
     */
    void flipCard(int row, int col) {
        getCard(row, col).flip();
        setChanged();
        notifyObservers();
    }

    void allFaceDown() {
        for (Card[] c : cards) {
            for (Card card : c) {
                if (!card.isMatched()) {
                    card.flip();
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
