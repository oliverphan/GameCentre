//package fall2018.csc2017.connectfour;
//
//import org.junit.Test;
//
//import java.util.Iterator;
//import static org.junit.Assert.*;
//
//public class FourBoardManagerTest {
//
//    private FourBoardManager fourBoardManager;
//    private FourBoard fourBoard;
//
//    /**
//     * Initialize a new FourBoardManager with empty pieces.
//     */
//    private void makeNewBoardManager() {
//        fourBoardManager = new FourBoardManager(0);
//        fourBoard = fourBoardManager.getBoard();
//    }
//
//    /**
//     * Initialize a new FourBoardManager and set all the pieces to be one player's.
//     * @param i the player who wins this board.
//     */
//    private void makeFullBoardManager( int i){
//        fourBoardManager = new FourBoardManager(0);
//        fourBoard = new FourBoard();
//        for (Piece p : fourBoard) {
//            p.setPlayer(i);
//        }
//    }
//
//    /**
//     * Test whether or not the current player is either 1 or 2.
//     */
//    @Test
//    public void getCurPlayer() {
//        makeNewBoardManager();
//        assert fourBoardManager.getCurPlayer() == 1 || fourBoardManager.getCurPlayer() == 2;
//    }
//
//    /**
//     * Test that the score is 0 for an empty board (since the player hasn't won.
//     */
//    @Test
//    public void generateScoreEmpty() {
//        makeNewBoardManager();
//        assert fourBoardManager.generateScore() == 0;
//    }
//
//    /**
//     * Test that the score is correct if the player has won.
//     */
//    @Test
//    public void generateScorePlayerWins() {
//        makeFullBoardManager(1);
//        assert fourBoardManager.generateScore() == 100;
//    }
//
//    /**
//     * Test if the score when the AI wins is 0.
//     */
//    @Test
//    public void generateScoreAIWins(){
//        makeFullBoardManager(2);
//        assert fourBoardManager.generateScore() == 0;
//    }
//
//    /**
//     * Test if the game detects when the game is over.
//     */
//    @Test
//    public void gameFinished() {
//        makeFullBoardManager(1);
//        assert fourBoardManager.gameFinished();
//        makeNewBoardManager();
//        assert !fourBoardManager.gameFinished();
//    }
//
//    /**
//     * Test if isValidTap returns true unless there is no room in the column.
//     */
//    @Test
//    public void isValidTap() {
//        makeNewBoardManager();
//        assert fourBoardManager.isValidTap(0);
//        makeFullBoardManager(1);
//        assert !fourBoardManager.isValidTap(0);
//    }
//}