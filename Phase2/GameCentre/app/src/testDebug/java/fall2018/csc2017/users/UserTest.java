package fall2018.csc2017.users;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * A User for testing purposes.
     */
    private User testUser;

    /**
     * Creates the User to be tested.
     */
    private void makeUser() {
        this.testUser = new User("Name", "Password");
    }

    /**
     * Tests the getName() method.
     */
    @Test
    public void testGetName() {
        makeUser();
        String expected = "Name";
        assertEquals(expected, testUser.getName());
    }

    /**
     * Tests the getPassword() method.
     */
    @Test
    public void testGetPassword() {
        makeUser();
        String expected = "Password";
        assertEquals(expected, testUser.getPassword());
    }

    /**
     * Tests the getScores() method.
     */
    @Test
    public void testGetScores() {
        makeUser();
        testUser.setNewScore("Sliding Tiles", 100);
        assertTrue(testUser.getScores().containsKey("Sliding Tiles"));
        Integer expected = 100;
        assertEquals(expected, testUser.getScores().get("Sliding Tiles"));
    }

    /**
     * Tests teh setNewScore() method.
     */
    @Test
    public void testSetNewScore() {
        makeUser();
        testUser.setNewScore("Connect Four", 100);
        Integer expected = 100;
        assertEquals(expected, testUser.getScores().get("Connect Four"));

        testUser.setNewScore("Connect Four", 1000);
        expected = 1000;
        assertEquals(expected, testUser.getScores().get("Connect Four"));
    }
}