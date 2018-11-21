package fall2018.csc2017.Memory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage a MemoryBoard, checking for win, and managing taps.
 */
public class MemoryBoardManager implements Serializable {

    /**
     * The MemoryBoard being managed.
     */
    private MemoryBoard memoryBoard;

    /**
     * the number of moves made so far.
     */
    private int numMoves;

    /**
     * The number of Cards per side of the MemoryBoard.
     */
    private int difficulty;

    /**
     * The number of Cards in the MemoryBoard.
     */
    private final int numCards;

    /**
     * Manage a new MemoryBoard with the specified difficulty.
     *
     * @param difficulty the difficulty of this MemoryBoard
     */
    public MemoryBoardManager(int difficulty) {
        this.numMoves = 0;
        this.difficulty = difficulty;
        this.numCards = difficulty * difficulty;
        setDifficulty(difficulty);
    }

    /**
     *  THe name of this game is "Memory".
     *
     * @return the name of this game
     */
    public String getName() {
        return "Memory Tiles";
    }

    /**
     * Return the MemoryBoard being managed.
     *
     * @return the MemoryBoard
     */
    MemoryBoard getMemoryBoard() {
        return this.memoryBoard;
    }

    /**
     * Return the difficulty ogf this game.
     *
     * @return the difficulty
     */
    int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Return the amount of moves which have been made so far.
     *
     * @return how many moves have been made
     */
    int getNumMoves() {
        return this.numMoves;
    }

    /**
     * Return the final Score of this game.
     *
     * @return the final Score of the game
     */
    int generateScore() {
        return 1;
        // TODO
    }

    /**
     * Set the difficulty for this MemoryBoard, and generate a new board based on the difficulty.
     *
     * @param difficulty the difficulty of this MemoryBoard
     */
    private void setDifficulty(int difficulty) {
        List<Card> cards = new ArrayList<>();
        for (int cardNum = 0; cardNum != numCards; cardNum++) {
            cards.add(new Card(cardNum));
        }
        this.memoryBoard = new MemoryBoard(cards);
        shuffle(difficulty);
    }

    /**
     * Shuffles the Cards in this MemoryBoard to random positions.
     *
     * @param difficulty the difficulty of this MemoryBoard
     */
    private void shuffle(int difficulty) {
        //TODO
    }


}
