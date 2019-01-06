package fall2018.csc2017.connectfour;

import org.junit.Test;

public class FourBoardManagerTest {

    /**
     * Initialize a new FourBoardManager and set all the pieces to be one player's, except
     * for the first column which will be empty.
     * @param i the player who wins this board.
     * @return the new FourBoardManager
     */
    private FourBoardManager makeFullBoardManager( int i){
        FourBoardManager fourBoardManager = new FourBoardManager(0);
        for (int col = 0; col < 7; col++){
            for (int row = 0; row < 6; row++){
                if(col != 0) {
                    fourBoardManager.getBoard().placePiece(col, i);
                }
            }
        }
        return fourBoardManager;
    }

    /**
     * Test whether or not the current player is either 1 or 2.
     */
    @Test
    public void getCurPlayer() {
        FourBoardManager fourBoardManager = new FourBoardManager(0);
        assert fourBoardManager.getCurPlayer() == 1 || fourBoardManager.getCurPlayer() == 2;
    }

    /**
     * Test that the score is 0 for an empty board (since the player hasn't won.
     */
    @Test
    public void generateScoreEmpty() {
        FourBoardManager fourBoardManager = new FourBoardManager(0);
        assert fourBoardManager.generateScore() == 100;
    }

    /**
     * Test that the score is correct if the player has won.
     */
    @Test
    public void generateScorePlayerWins() {
        FourBoardManager fourBoardManager = makeFullBoardManager(1);
        fourBoardManager.touchMove(0);
        assert fourBoardManager.generateScore() == 96;
    }

    /**
     * Test that the score is correct if the player has lost.
     */
    @Test
    public void generateScorePlayerLoses(){
        FourBoardManager fourBoardManager = makeFullBoardManager(2);
        fourBoardManager.touchMove(0);
        assert fourBoardManager.generateScore() == 0;
    }

    /**
     * Test if the game detects when the game is over.
     */
    @Test
    public void gameFinished() {
        FourBoardManager fourBoardManager = makeFullBoardManager(1);
        fourBoardManager.touchMove(0);
        assert !fourBoardManager.gameFinished();
        FourBoardManager emptyFourBoardManager = new FourBoardManager(0);
        assert emptyFourBoardManager.gameFinished();
    }

    /**
     * Test if isValidTap returns correct values.
     */
    @Test
    public void isValidTap() {
        FourBoardManager fourBoardManager = makeFullBoardManager(1);
        if(fourBoardManager.getCurPlayer() == 1) {
            assert fourBoardManager.isValidTap(0);
            assert !fourBoardManager.isValidTap(1);
        }
        else{
            assert !fourBoardManager.isValidTap(1);
        }
    }
}