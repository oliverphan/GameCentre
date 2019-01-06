package fall2018.csc2017.connectfour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class ComputerPlayer implements Serializable {
    /**
     * The board being managed.
     */
    private FourBoard board;

    /**
     * The difficulty set for the game
     */
    private int difficulty;

    /**
     * Initialize the AI player for a game
     *
     * @param board the board the AI will be playing on
     * @param d     the difficulty set for the AI
     */
    ComputerPlayer(FourBoard board, int d) {
        this.board = board;
        this.difficulty = d;
    }

    /**
     * Main method for AI player to make a smart move.
     *
     * @return return the best move (or random move if all moves result in a loss).
     */
    int getComputerMove() {
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
    private List<Integer> getPotentialMoves(FourBoard board, int d) {
        if (d == 0 || board.isBoardFull())
            return getZeroList();
        List<Integer> moves = getZeroList();
        for (int move : getLegalMoves(board)) {
            FourBoard dupe = new FourBoard(board.pieces);
            dupe.makeDupeMove(move, 2);
            if (dupe.isWinner(2)) {
                moves.set(move, 1);
                break;
            } else {
                for (int eMove : getLegalMoves(dupe)) {
                    FourBoard dupe2 = new FourBoard(dupe.pieces);
                    dupe2.makeDupeMove(eMove, 1);
                    if (dupe2.isWinner(1)) {
                        moves.set(move, -1);
                        break;
                    } else {
                        List<Integer> results = getPotentialMoves(dupe2, d - 1);
                        moves.set(move, moves.get(move) + sumList(results) / (int) (Math.pow(7, difficulty)));
                    }
                }
            }
        }
        return moves;
    }

    /**
     * Takes a list and returns the indices of the elements that match the provided int.
     *
     * @param list     The list being checked
     * @param maxScore the int being compared to
     * @return list of indices that equal the int
     */
    private List<Integer> getMaxElements(List<Integer> list, int maxScore) {
        List<Integer> maxElements = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == maxScore && board.openRow(i) != -1) {
                maxElements.add(i);
            }
        }
        return maxElements;
    }

    /**
     * Returns a list of columns that are legal moves in a given board
     *
     * @param board the board being checked
     * @return a list of columns that represent valid moves
     */
    private List<Integer> getLegalMoves(FourBoard board) {
        List<Integer> allowedMoves = new ArrayList<>();
        for (int i = 0; i < board.getNumCols(); i++) {
            if (board.openRow(i) != -1) {
                allowedMoves.add(i);
            }
        }
        return allowedMoves;
    }

    /**
     * Creates a list of zeroes.
     *
     * @return list of zeroes for each column
     */
    private List<Integer> getZeroList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < board.getNumCols(); i++) {
            list.add(0);
        }
        return list;
    }

    /**
     * Returns a sum of all the elements in a given list
     *
     * @param list the list passed in
     * @return the sum of all elements
     */
    private int sumList(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    /**
     * Returns the best score of all legals moves in a given list
     *
     * @param list the list passed in
     * @return the best score among all legal moves
     */
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
