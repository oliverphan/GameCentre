//package fall2018.csc2017.connectfour;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class FourBoardTest {
//
//    /**
//     * The board manager for testing.
//     */
//    private FourBoard fourBoard;
//
//
//    /**
//     * Initialize a new FourBoard with empty pieces.
//     */
//    private void makeEmptyBoard() {
//        this.fourBoard = new FourBoard();
//
//    }
//
//    /**
//     * Initialize a new FourBoard with a piece in every position of board.
//     */
//    private void makeFullBoard() {
//        makeEmptyBoard();
//        Piece[][] testPieces = new Piece[fourBoard.getNumCols()][fourBoard.getNumRows()];
//        for (int col = 0; col < fourBoard.getNumCols(); col++) {
//            for (int row = 0; row < fourBoard.getNumRows(); row++) {
//                testPieces[col][row] = new Piece();
//                testPieces[col][row].setPlayer(1);
//            }
//        }
//        this.fourBoard = new FourBoard(testPieces);
//    }
//
//
//    /**
//     * Tests whether if isWinner returns true when the game has been won.
//     */
//    @Test
//    public void isWinner() {
//        makeEmptyBoard();
//        assertFalse(fourBoard.isWinner(1));
//        fourBoard.placePiece(1, 1);
//        fourBoard.placePiece(1, 1);
//        fourBoard.placePiece(1, 1);
//        fourBoard.placePiece(1, 1);
//        assertTrue(fourBoard.isWinner(1));
//        assertFalse(fourBoard.isWinner(2));
//
//    }
//
//    /**
//     * Tests whether if the board is full.
//     */
//    @Test
//    public void isBoardFull() {
//        makeEmptyBoard();
//        assertFalse(fourBoard.isBoardFull());
//        makeFullBoard();
//        assertTrue(fourBoard.isBoardFull());
//
//    }
//
//    /**
//     * Tests whether if openRow works as attended.
//     */
//    @Test
//    public void openRow() {
//        makeEmptyBoard();
//        assertEquals(5, fourBoard.openRow(1));
//
//        // Place a piece in column 2
//        fourBoard.placePiece(2, 1);
//        assertEquals(4, fourBoard.openRow(2));
//
//        //Place a piece in all rows of column 3
//        fourBoard.placePiece(3, 1);
//        fourBoard.placePiece(3, 1);
//        fourBoard.placePiece(3, 1);
//        fourBoard.placePiece(3, 1);
//        fourBoard.placePiece(3, 1);
//        fourBoard.placePiece(3, 1);
//        assertEquals(-1, fourBoard.openRow(3));
//    }
//
//    /**
//     * Tests whether if placePiece method works as intended.
//     */
//    @Test
//    public void placePiece() {
//        makeEmptyBoard();
//        fourBoard.placePiece(1, 1);
//        //place a piece in column 1 from player 1
//        Piece piece = fourBoard.getPiece(1, fourBoard.getNumRows()-1);
//        assertEquals(1, piece.getPlayer());
//        assertEquals(2, fourBoard.curPlayer);
//    }
//
//    /**
//     * Tests whether if switchPlayer method switches players.
//     */
//    @Test
//    public void switchPlayer() {
//        makeEmptyBoard();
//        fourBoard.placePiece(1, 1);
//        assertEquals(2, fourBoard.curPlayer);
//
//    }
//
//    /**
//     * Tests whether if getPiece method returns the correct piece on the board.
//     */
//    @Test
//    public void getPiece() {
//        makeEmptyBoard();
//        fourBoard.placePiece(1, 1);
//        Piece piece = fourBoard.getPiece(1, fourBoard.getNumRows()-1);
//        assertEquals(1, piece.getPlayer());
//        // Get an empty piece.
//        Piece emptyPiece = fourBoard.getPiece(2, fourBoard.getNumRows()-1);
//        assertEquals(0, emptyPiece.getPlayer());
//
//    }
//}