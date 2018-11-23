package fall2018.csc2017.connectFour;

import java.io.Serializable;
import java.util.Observable;

public class FourBoard extends Observable implements Serializable {
    Piece[][] pieces;
    final int NUM_COLS = 7;
    final int NUM_ROWS = 6;
    int curPlayer;

    public FourBoard() {
        this.pieces = new Piece[NUM_COLS][NUM_ROWS];
        createBoard();
        curPlayer = 1;
    }

    public FourBoard(Piece[][] pieces) {
        this.pieces = new Piece[NUM_COLS][NUM_ROWS];
        for (int col = 0; col < NUM_COLS; col++) {
            for (int row = 0; row < NUM_ROWS; row++) {
                this.pieces[col][row] = new Piece();
                this.pieces[col][row].setPlayer(pieces[col][row].getPlayer());
            }
        }
    }

//    private void initPlayer() {
//        this.curPlayer = new Random().nextInt(2) + 1;
//    }

    private void createBoard() {
        for (int col = 0; col < NUM_COLS; col++) {
            for (int row = 0; row < NUM_ROWS; row++) {
                pieces[col][row] = new Piece();
            }
        }
    }

    public boolean isWinner(int player) {
        return winHorizontal(player) || winVertical(player) || winDRight(player) || winDLeft(player);
    }

    private boolean winHorizontal(int player) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS - 3; col++) {
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

    private boolean winVertical(int player) {
        for (int col = 0; col < NUM_COLS; col++) {
            for (int row = 0; row < NUM_ROWS - 3; row++) {
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

    private boolean winDRight(int player) {
        for (int col = 0; col < NUM_COLS - 3; col++) {
            for (int row = 3; row < NUM_ROWS; row++) {
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

    private boolean winDLeft(int player) {
        for (int col = 0; col < NUM_COLS - 3; col++) {
            for (int row = 0; row < NUM_ROWS - 3; row++) {
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

    public boolean isBoardFull() {
        for (int col = 0; col < NUM_COLS; col++) {
            for (int row = 0; row < NUM_ROWS; row++) {
                if (pieces[col][row].getPlayer() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    int openRow(int col) {
        for (int row = 5; row > -1; row--) {
            if (pieces[col][row].getPlayer() == 0) {
                return row;
            }
        }
        return -1;
    }

    void makeMove(int col, int player) {
        pieces[col][openRow(col)].setPlayer(player);
        switchPlayer();
        setChanged();
        notifyObservers();
    }

//    void undoMove(int col) {
//        for (int row = 0; row < NUM_ROWS; row++) {
//            if (pieces[col][row].getPlayer() != 0) {
//                pieces[col][row].setPlayer(0);
//            }
//        }
//        setChanged();
//        notifyObservers();
//    }

    void switchPlayer(){
        curPlayer = curPlayer == 1 ? 2 : 1;
    }

    Piece getPiece(int col, int row){
        return pieces[col][row];
    }
}