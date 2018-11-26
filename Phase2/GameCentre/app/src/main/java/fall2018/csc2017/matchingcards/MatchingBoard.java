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
                this.cards[row][col] = next;
            }
        }

    }

    /**
     * @param row
     * @param col
     * @return
     */
    Card getCard(int row, int col) {
        return cards[row][col];
    }

    /**
     * @return
     */
    Card[][] getCards() {
        return this.cards;
    }

    /**
     * Flip a card in the board.
     *
     * @param card the card to be flipped in the board
     */
    void flipCard(Card card, String s) {
        System.out.println("THE CARD FLIPPED IS: " + s);
        card.flip();
        setChanged();
        notifyObservers();
    }

    /**
     *
     */
    void allFaceDown() {
        for (Card[] c : cards) {
            for (Card card : c) {
                card.flip();
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
