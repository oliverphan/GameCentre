package fall2018.csc2017.connectFour;

import java.io.Serializable;
import java.util.Stack;

public class FourBoardManager implements Serializable {
    private FourBoard board;
    private int numMoves;
//    Stack<Integer> previousMoves;
    private int difficulty;

    FourBoard getBoard(){
        return this.board;
    }

    public FourBoardManager(int d){
        difficulty = d;
//        previousMoves = new Stack<>();
        board = new FourBoard();
    }

    boolean isValidTap(int position){
        int col = position % board.NUM_COLS;
        return board.openRow(col) > -1;
    }

    void makeMove(int position){
        int col = position % board.NUM_COLS;
        if (board.openRow(col) > -1){
            numMoves++;
//            previousMoves.push(col);
            board.makeMove(col, board.curPlayer);
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int generateScore(){
        return 100*difficulty*numMoves - (board.isWinner(1) ? 0 : 10*difficulty);
    }
//    void undoMove(int numToUndo) {
//        if (!gameFinished()) {
//            for (int i = 0; i < numToUndo; i++) {
//                if (previousMoves.isEmpty())
//                    return;
//                if (board.curPlayer == 1 && numMoves >= 2) {
//                    int tmp = previousMoves.pop();
//                    board.undoMove(tmp);
//                    int tmp2 = previousMoves.pop();
//                    board.undoMove(tmp2);
//                    numMoves -= 2;
//                }else{
//                    int tmp = previousMoves.pop();
//                    board.undoMove(tmp);
//                    numMoves--;
//                }
//            }
//        }
//    }

    boolean gameFinished(){
        return board.isBoardFull() || board.isWinner(1) || board.isWinner(2);
    }
}
