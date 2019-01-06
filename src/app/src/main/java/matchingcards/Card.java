package fall2018.csc2017.matchingcards;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

import fall2018.csc2017.R;
import fall2018.csc2017.common.Token;

/**
 * A Card in a Matching Cards game.
 */
public class Card extends Token implements Serializable {

    /**
     * This Cards unique id.
     */
    private int id;

    /**
     * Whether or not this Card is face down.
     */
    private boolean faceDown;

    /**
     * Whether or not this Card has been matched during a Matching Cards game.
     */
    private boolean matched;


    /**
     * A Card with a background id. Sets each new Card to be facing down and unmatched.
     * Sets the 'back' of the Cards to all be the same.
     *
     * @param backgroundId the background id of the Card to create
     */
    Card(int backgroundId) {
        super(backgroundId + 1);
        this.id = backgroundId + 1;
        this.faceDown = true;
        this.matched = false;
        this.background = R.drawable.card_0;
    }

    /**
     * Return the id of this Card.
     *
     * @return the integer which belongs to this Card
     */
    public int getId() {
        return id;
    }

    /**
     * Returns whether or not this Card is face up or face down.
     *
     * @return Whether or not this Card is faceDown
     */
    boolean isFaceDown() {
        return faceDown;
    }

    /**
     * Set this Card to be face up, or face down, depending on its current state.
     * Changes the image resource to be displayed on this Card.
     */
    void flip() {
        this.faceDown = !this.faceDown;
        String uri = "card_" + this.id;
        try {
            Class res = R.drawable.class;
            Field field = res.getField(uri);
            background = faceDown ? R.drawable.card_0 : field.getInt(null);
        } catch (IllegalAccessException e) {
            Log.e("DrawableAccess", "Failed to get resource by id", e);
        } catch (NoSuchFieldException e) {
            Log.e("DrawableAccess", "No such image", e);
        }
    }

    /**
     * Return whether or not this Card has been matched with another.
     *
     * @return If this Card has been matched
     */
    boolean isMatched() {
        return matched;
    }

    /**
     * Set this Cards matched status to be true.
     */
    void setMatched() {
        this.matched = true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) {
            return false;
        }
        Card obj = (Card) o;
        return obj.id == this.id;
    }
}