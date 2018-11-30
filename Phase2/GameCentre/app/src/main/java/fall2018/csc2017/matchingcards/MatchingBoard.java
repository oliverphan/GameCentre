package fall2018.csc2017.matchingcards;

import java.util.List;

import fall2018.csc2017.common.Board;

/**
 * The MatchingBoard tiles board.
 */
class MatchingBoard extends Board<Card> {

    /**
     * The cards on the board in row-major order.
     */
    private final Card[][] cards;


    /**
     * A new MatchingBoard in row-major order.
     * Precondition: len(cards) == numRows * numCols
     *
     * @param cards the cards contained in this MatchingBoard
     */
    MatchingBoard(List<Card> cards) {
        super(cards);
        this.cards = super.tokens;
    }

    /**
     * Return the Card at the given location in this Matching Board.
     *
     * @param row the row location of the desired Card
     * @param col the col location of the desired Card
     * @return the card at the location (row, col) in this Matching Board
     */
    Card getCard(int row, int col) {
        return cards[row][col];
    }


    /**
     * Returns the cards in this Matching Board.
     *
     * @return the cards in this Matching Board, in row descending order.
     */
    Card[][] getCards() {
        return this.cards;
    }

    /**
     * Flip a card in the board.
     *
     * @param card the card to be flipped in the board
     */
    void flipCard(Card card) {
        card.flip();
        setChanged();
        notifyObservers();
    }

    /**
     * Set the Card targetCards isMatched status to true.
     *
     * @param targetCard the Card to change the status of
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
