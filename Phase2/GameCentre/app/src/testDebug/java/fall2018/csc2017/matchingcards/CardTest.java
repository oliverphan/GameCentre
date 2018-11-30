package fall2018.csc2017.matchingcards;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    /**
     * A Card for testing purposes.
     */
    private Card testCard;

    /**
     * Creates the Card to be tested.
     */
    private void makeCard() {
        this.testCard = new Card(1);
    }

    /**
     * Tests the getBackground method from the parent class.
     */
    @Test
    public void testGetBackground() {
        makeCard();
        assertEquals(2131230817, testCard.getBackground());
    }
    /**
     * Tests the getId() method.
     */
    @Test
    public void testGetId() {
        makeCard();
        assertEquals(2, testCard.getId());
    }

    /**
     * Tests the isFaceDown() method.
     */
    @Test
    public void testIsFaceDown() {
        makeCard();
        assertTrue(testCard.isFaceDown());
        testCard.flip();
        assertFalse(testCard.isFaceDown());
    }

    /**
     * Tests the flip() method.
     */
    @Test
    public void testFlip() {
        makeCard();
        assertTrue(testCard.isFaceDown());
        testCard.flip();
        assertFalse(testCard.isFaceDown());

    }

    /**
     * Tests the flipBask() method.
     */
    @Test
    public void testFlipBack() {
        makeCard();
        testCard.flip();
        assertFalse(testCard.isFaceDown());
        testCard.flip();
        assertTrue(testCard.isFaceDown());
    }

    /**
     * Tests the isMatched() method.
     */
    @Test
    public void testIsMatched() {
        makeCard();
        assertFalse(testCard.isMatched());
        testCard.setMatched();
        assertTrue(testCard.isMatched());
    }

    /**
     * Tests the setMatched() method.
     */
    @Test
    public void testSetMatched() {
        makeCard();
        assertFalse(testCard.isMatched());
        testCard.setMatched();
        assertTrue(testCard.isMatched());
    }

    /**
     * Tests the equals() method.
     */
    @Test
    public void testEquals() {
        makeCard();
        Card compareCard = new Card(1);
        assertEquals(testCard, compareCard);
        assertNotEquals(testCard, "");
    }
}