package fall2018.csc2017.matchingcards;

import android.support.annotation.NonNull;

import fall2018.csc2017.common.Token;

/**
 * A card for the Concentration game.
 */
public class Card extends Token implements Comparable<Card> {

    /**
     * This Cards unique id.
     */
    private int id;

    /** whether or not this Card is face down.
     *
     */
    private boolean faceDown;

    /**
     * Whether or not this Card has been matched during a Concentration game.
     */
    private boolean matched;

    Card(int backgroundId) {
        super(backgroundId);
        this.id = backgroundId;
        this.faceDown = true;
        this.matched = false;
    }

    /**
     * Return the id of this Card.
     *
     * @return the integer which belongs to this Card
     */
    public int getId() {
        return id;
    }

    boolean isFaceDown() {
        return faceDown;
    }

    void setFaceDown(boolean bool) {
        this.faceDown = bool;
    }

    boolean isMatched() {
        return matched;
    }

    //Method to display back or front of card ???


    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }

}
