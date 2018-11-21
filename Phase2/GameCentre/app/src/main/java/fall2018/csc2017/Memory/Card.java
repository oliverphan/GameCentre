package fall2018.csc2017.Memory;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

import fall2018.csc2017.slidingtiles.R;

/**
 * A card for the Memory game.
 */
public class Card implements Comparable<Card>, Serializable {
    /**
     * The background id, used to find this Card's image.
     */
    private int background;

    /**
     * This Cards unique id.
     */
    private int id;

    /**
     * Return the background id of this Card.
     *
     * @return the integer representation of this Card's background
     */
    public int getBackground() {
        return background;
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
     * A Card with a background id; look up and set the id.
     * Adapted from http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
     *
     * @param backgroundId the id of the Card
     */
    Card(int backgroundId) {
        this.id = backgroundId;
        String uri = "card_" + String.valueOf(id);
        try {
            Class res = R.drawable.class;
            Field field = res.getField(uri);
            this.background = field.getInt(null);
        } catch (Exception e) {
            Log.e("DrawableAccess", "Failed to get resource by id", e);
        }
    }


    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }

}
