package fall2018.csc2017.matchingcards;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

public class MatchingBoardTest {


    /**
     * A MatchingBoard for testing purposes.
     */
    private MatchingBoard testMB;

    /**
     * List of 12 Cards unordered.
     */
    private List<Card> cards;

    /**
     * Creates the MatchingBoard with Cards to be tested.
     */
    private void makeMB() {
        makeCards();
        this.testMB = new MatchingBoard(cards);
    }

    private void makeCards() {
        // Make a list of 12 cards
        cards = new ArrayList<>();
        for (int cardNum = 0; cardNum != 12 / 2; cardNum++) {
            cards.add(new Card(cardNum));
            cards.add(new Card(cardNum));
        }
    }

    /**
     * Test whether the getCard() gets the correct Card based on comparing ID.
     */
    @Test
    public void testGetCardByID() {
        makeMB();
        Card tmp1 = testMB.getCard(0, 0);
        Card tmp2 = new Card(0);
        assertEquals(tmp1.getId(), tmp2.getId());
        Card tmp3 = testMB.getCard(3, 2);
        Card tmp4 = new Card(5);
        assertEquals(tmp3.getId(), tmp4.getId());
    }

    /**
     * Test whether the getCards() gets the correct Card 2D array by ID of Cards.
     */
    @Test
    public void testGetCards() {
        makeMB();
        Card[][] tmp1 = testMB.getCards();
        int[][] tmp2 = {{1, 1, 2}, {2, 3, 3}, {4, 4, 5}, {5, 6, 6}};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(tmp1[i][j].getId(), tmp2[i][j]);
            }
        }
    }

    /**
     * Test whether the Card passed in the MatchingBoard was flipped successfully.
     */
    @Test
    public void testFlipCard() {
        makeMB();
        Card tmp = cards.get(0);
        assertTrue(tmp.isFaceDown());
        testMB.flipCard(cards.get(0));
        assertFalse(tmp.isFaceDown());
    }

    /**
     * Test whether the Card passed in the MatchingBoard was set to matched successfully.
     */
    @Test
    public void setCardMatched() {
        makeMB();
        Card tmp = cards.get(11);
        assertFalse(tmp.isMatched());
        testMB.setCardMatched(tmp);
        assertTrue(tmp.isMatched());
    }
}