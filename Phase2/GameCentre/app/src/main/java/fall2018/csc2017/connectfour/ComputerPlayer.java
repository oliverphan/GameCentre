package fall2018.csc2017.connectfour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComputerPlayer implements Serializable {
    private FourBoard board;
    private int difficulty;

    public ComputerPlayer(FourBoard board, int d) {
        this.board = board;
        this.difficulty = d;
    }

    /**
     * Main method for AI player to make a smart move.
     *
     * @return return the best move (or random move if all moves result in a loss).
     */
    public int getComputerMove() {
        List<Integer> potentialMoves = getPotentialMoves(board, difficulty);
        int bestMoveScore = getBestLegalMoveScore(potentialMoves);
        List<Integer> bestMoves = getMaxElements(potentialMoves, bestMoveScore);
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }

    /**
     * Helper method to evaluate and return the best scores of moves for a given board.
     *
     * @param board the board being evaluated
     * @param d     amount of moves to look ahead.
     * @return list of scores for potential moves
     */
    public List<Integer> getPotentialMoves(FourBoard board, int d) {
        if (d == 0 || board.isBoardFull())
            return getZeroList();
        List<Integer> potentialMoves = getZeroList();
        for (int move : getLegalMoves(board)) {
            FourBoard dupe = new FourBoard(board.pieces);
            dupe.makeDupeMove(move, 2);
            if (dupe.isWinner(2)) {
                potentialMoves.set(move, 1);
                break;
            } else {
                for (int eMove : getLegalMoves(dupe)) {
                    FourBoard dupe2 = new FourBoard(dupe.pieces);
                    dupe2.makeDupeMove(eMove, 1);
                    if (dupe2.isWinner(1)) {
                        potentialMoves.set(move, -1);
                        break;
                    } else {
                        List<Integer> results = getPotentialMoves(dupe2, d - 1);
                        potentialMoves.set(move, potentialMoves.get(move) + sumList(results) / 7 / 7);
                    }
                }
            }
        }
        return potentialMoves;
    }

    private List<Integer> getMaxElements(List<Integer> list, int maxScore) {
        List<Integer> maxElements = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == maxScore && board.openRow(i) != -1) {
                maxElements.add(i);
            }
        }
        return maxElements;
    }

    private List<Integer> getLegalMoves(FourBoard board) {
        List<Integer> allowedMoves = new ArrayList<>();
        for (int i = 0; i < board.getNumCols(); i++) {
            if (board.openRow(i) != -1) {
                allowedMoves.add(i);
            }
        }
        return allowedMoves;
    }

    private List<Integer> getZeroList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(0);
        }
        return list;
    }

    private int sumList(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    private int getBestLegalMoveScore(List<Integer> list) {
        List<Integer> legalMoves = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (board.openRow(i) != -1) {
                legalMoves.add(list.get(i));
            }
        }
        return Collections.max(legalMoves);
    }
}
