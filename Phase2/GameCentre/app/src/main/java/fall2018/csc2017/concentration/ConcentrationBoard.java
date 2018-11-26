package fall2018.csc2017.concentration;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The ConcentrationBoard tiles board.
 */
public class ConcentrationBoard extends Board<Card> {

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * A new ConcentrationBoard in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param cards the cards contained in this ConcentrationBoard
     */
    ConcentrationBoard(List<Card> cards) {
        super(cards);
        this.cards = super.tokens;
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
        return "ConcentrationBoard{" +
                "cards=" + Arrays.toString(cards) +
                "}";
    }
}