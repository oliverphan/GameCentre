package fall2018.csc2017.matchingcards;

import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The MatchingBoard tiles board.
 */
public class MatchingBoard extends Board<Card> {

    /**
     * The Cards on the board in row-major order.
     */
    private Card[][] cards;


    /**
     * A new MatchingBoard in row-major order.
     * Precondition: len(cards) == numRows * numCols
     *
     * @param cards the Cards contained in this MatchingBoard
     */
    MatchingBoard(List<Card> cards) {
        super(cards);
        this.cards = super.tokens;
    }

    /**
     * Return the Card at the given location in this MatchingBoard.
     *
     * @param row the row location of the desired Card
     * @param col the col location of the desired Card
     * @return the Card at the location (row, col) in this MatchingBoard
     */
    Card getCard(int row, int col) {
        return cards[row][col];
    }


    /**
     * Returns the Cards in this MatchingBoard.
     *
     * @return the Cards in this MatchingBoard, in row-major order.
     */
    Card[][] getCards() {
        return this.cards;
    }

    /**
     * Flip a Card in the Board.
     *
     * @param card the Card to be flipped in the Board
     */
    void flipCard(Card card) {
        card.flip();
        setChanged();
        notifyObservers();
    }

    /**
     * Set the Card targetCard to be matched.
     *
     * @param targetCard the Card to change the isMatched status of
     */
    void setCardMatched(Card targetCard) {
        for (Card[] row : this.cards) {
            for (Card c : row) {
                if (c == targetCard) {
                    c.setMatched();
                    setChanged();
                    notifyObservers();
                }
            }
        }
    }
}
