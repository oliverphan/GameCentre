package fall2018.csc2017.scoring;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    private Score testScore;

    private void makeScore() {
        this.testScore = new Score("Username", 1000);
    }
    /**
     * Tests the getValue() method.
     */
    @Test
    public void testGetValue() {
        makeScore();
        assertEquals(1000, testScore.getValue());
    }

    /**
     * Tests the getUserName() method.
     */
    @Test
    public void testGetUsername() {
        makeScore();
        assertEquals("Username", testScore.getUsername());
    }

    /**
     * Tests the setValue() method.
     */
    @Test
    public void testSetValue() {
        makeScore();
        testScore.setValue(200);
        assertEquals(200, testScore.getValue());
    }

    /**
     * Tests the equals() method with equal Scores.
     */
    @Test
    public void testEquals() {
        makeScore();
        Score compareScore = new Score("Username", 1000);
        assertEquals(compareScore, testScore);
        assertNotEquals(compareScore, "");
    }

    /**
     * Tests the equals() method with unequal Scores.
     */
    @Test
    public void testNotEquals() {
        makeScore();
        Score compareScore = new Score("My Name", 1000);
        assertNotEquals(compareScore, testScore);
    }

    /**
     * Tests the equals() method with a different object.
     */
@Test
public void testEqualsDifferentType() {
    makeScore();
    String compare = "This isn't a Score";
    assertNotEquals(compare, testScore);
}
    /**
     * Tests the toString() method.
     */
    @Test
    public void testToString() {
        makeScore();
        System.out.println(testScore.toString());
        assertEquals("Username : 1000", testScore.toString());
    }
}