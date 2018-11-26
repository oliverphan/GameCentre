package fall2018.csc2017.matchingcards;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

import fall2018.csc2017.R;
import fall2018.csc2017.common.Token;

/**
 * A card for the Concentration game.
 */
public class Card extends Token implements Serializable {

    /**
     * This Cards unique id.
     */
    private int id;

    /**
     * whether or not this Card is face down.
     */
    private boolean faceDown;

    /**
     * Whether or not this Card has been matched during a Concentration game.
     */
    private boolean matched;

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

    boolean isFaceDown() {
        return faceDown;
    }

    void flip() {
        this.faceDown = !this.faceDown;
        String uri = "card_" + this.id;
        try {
            Class res = R.drawable.class;
            Field field = res.getField(uri);
            background = faceDown ? R.drawable.card_0 : field.getInt(null);
        } catch (Exception e) {
            Log.e("DrawableAccess", "Failed to get resource by id", e);
        }
    }

    boolean isMatched() {
        return matched;
    }

    //Method to display back or front of card ???


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Card) {
            Card obj = (Card) o;
            return obj.id == this.id;
        }
        return false;
    }
}