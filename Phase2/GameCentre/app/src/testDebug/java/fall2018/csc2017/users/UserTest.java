package fall2018.csc2017.users;

import org.junit.Test;

import fall2018.csc2017.common.BoardManager;
import fall2018.csc2017.slidingtiles.SlidingBoardManager;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * A User for testing purposes.
     */
    private User testUser;

    /**
     *
     */
    private BoardManager testManager;

    /**
     * Creates the User to be tested.
     */
    private void makeUser() {
        this.testUser = new User("Name", "Password");
        this.testManager = new SlidingBoardManager(4);
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
     * Tests the setNewScore() method.
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

    /**
     * Tests the setNewScore() method with a game that doesn't exist.
     */
    @Test
    public void testSetNewScoreNoGame() {
        makeUser();
        testUser.setNewScore("Chess", 1000);
        Integer expected = 1000;
        assertTrue(testUser.getScores().containsKey("Chess"));
        assertEquals(expected, testUser.getScores().get("Chess"));
    }

    /**
     * Tests the setScore() method for a game which doesn't exist.
     */
    @Test
    public void testSetNewScoreNewGame() {
        makeUser();
        testUser.setNewScore("Test Game", 100);
        Integer expected = 100;
        assertEquals(expected, testUser.getScores().get("Test Game"));
    }

    /**
     * Tests the getSaves() method.
     */
    @Test
    public void testGetSaves() {
        makeUser();
        testUser.writeGame("Test Game", testManager);
        assertTrue(testUser.getSaves().containsKey("Test Game"));
    }

    /**
     * Tests the deleteSave() method.
     */
    @Test
    public void testDeleteSave() {
        makeUser();
        testUser.writeGame("Test Game", testManager);
        testUser.deleteSave("Test Game");
        assertFalse(testUser.getSaves().containsKey("Test Game"));

    }

    /**
     * Tests the writeGame() method.
     */
    @Test
    public void testWriteGame() {
        makeUser();
        testUser.writeGame("Test Game", testManager);
        assertTrue(testUser.getSaves().containsKey("Test Game"));
        assertEquals(testUser.getSaves().get("Test Game"), testManager);
    }

}