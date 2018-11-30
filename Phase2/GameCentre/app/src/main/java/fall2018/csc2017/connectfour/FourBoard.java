package fall2018.csc2017.connectfour;

import fall2018.csc2017.common.Board;

public class FourBoard extends Board<Piece> {

    /**
     * The pieces on the board.
     */
    Piece[][] pieces;

    /**
     * Initialize a new FourBoard with empty pieces.
     */
    FourBoard() {
        super();
        this.pieces = super.tokens;
        createBoard();
    }

    /**
     * Initialize a new FourBoard with the pieces in place.
     * Used to calculate potential future moves.
     */
    FourBoard(Piece[][] pieces) {
        this.pieces = new Piece[numCols][numRows];
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                this.pieces[col][row] = new Piece();
                this.pieces[col][row].setPlayer(pieces[col][row].getPlayer());
            }
        }
    }

    /**
     * Fill the board with empty pieces.
     */
    private void createBoard() {
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                pieces[col][row] = new Piece();
            }
        }
    }

    /**
     * Return whether the player has met the winning condition or not (lining up four pieces).
     *
     * @param player the player to check. 1 for human player, 2 for computer
     * @return true if this player has met a win condition
     */
    public boolean isWinner(int player) {
        return winHorizontal(player) || winVertical(player)
                || winDRight(player) || winDLeft(player);
    }

    /**
     * Return whether this player has won lining 4 Pieces horizontally.
     *
     * @param player the player to check. 1 for human player, 2 for computer
     * @return true if this player has aligned 4 Pieces horizontally
     */
    private boolean winHorizontal(int player) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols - 3; col++) {
                if (pieces[col][row].getPlayer() == player &&
                        pieces[col + 1][row].getPlayer() == player &&
                        pieces[col + 2][row].getPlayer() == player &&
                        pieces[col + 3][row].getPlayer() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether this player has won lining 4 Pieces vertically.
     *
     * @param player the player to check. 1 for human player, 2 for computer
     * @return true if this player has aligned 4 Pieces vertically
     */
    private boolean winVertical(int player) {
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows - 3; row++) {
                if (pieces[col][row].getPlayer() == player &&
                        pieces[col][row + 1].getPlayer() == player &&
                        pieces[col][row + 2].getPlayer() == player &&
                        pieces[col][row + 3].getPlayer() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether this player has won lining 4 Pieces Diagonally Right.
     *
     * @param player the player to check. 1 for human player, 2 for computer
     * @return true if this player has aligned 4 Pieces Diagonally Right
     */
    private boolean winDRight(int player) {
        for (int col = 0; col < numCols - 3; col++) {
            for (int row = 3; row < numRows; row++) {
                if (pieces[col][row].getPlayer() == player &&
                        pieces[col + 1][row - 1].getPlayer() == player &&
                        pieces[col + 2][row - 2].getPlayer() == player &&
                        pieces[col + 3][row - 3].getPlayer() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether this player has won lining 4 Pieces Diagonally Left.
     *
     * @param player the player to check. 1 for human player, 2 for computer
     * @return true if this player has aligned 4 Pieces Diagonally Left
     */
    private boolean winDLeft(int player) {
        for (int col = 0; col < numCols - 3; col++) {
            for (int row = 0; row < numRows - 3; row++) {
                if (pieces[col][row].getPlayer() == player &&
                        pieces[col + 1][row + 1].getPlayer() == player &&
                        pieces[col + 2][row + 2].getPlayer() == player &&
                        pieces[col + 3][row + 3].getPlayer() == player) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return if the board is full.
     *
     * @return true iff all pieces belong to player 1 or player 2
     */
    public boolean isBoardFull() {
        for (int col = 0; col < numCols; col++) {
            for (int row = 0; row < numRows; row++) {
                if (pieces[col][row].getPlayer() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the column has an empty piece in any row.
     *
     * @param col the column to verify
     * @return -1 if all Pieces belong to a player
     */
    int openRow(int col) {
        for (int row = 5; row > -1; row--) {
            if (pieces[col][row].getPlayer() == 0) {
                return row;
            }
        }
        return -1;
    }

    /**
     * Place a Piece in the Board, and then switch to the next player's turn.
     *
     * @param col    the column to be placed in
     * @param player the player placing the Piece
     */
    void placePiece(int col, int player) {
        pieces[col][openRow(col)].setPlayer(player);
        setChanged();
        notifyObservers();
    }

    /***
     * Method to change a duplicate board without notifying observers.
     *
     * @param col the column to be placed in
     * @param player the player placing the Piece
     */
    void makeDupeMove(int col, int player) {
        pieces[col][openRow(col)].setPlayer(player);
    }

    /**
     * Get the specific Piece at the passed in column and row.
     *
     * @param col the column to take from
     * @param row the row to take from
     * @return a Piece
     */
    Piece getPiece(int col, int row) {
        return pieces[col][row];
    }
}
