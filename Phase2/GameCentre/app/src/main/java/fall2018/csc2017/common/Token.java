package fall2018.csc2017.common;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

import fall2018.csc2017.R;

public abstract class Token implements Serializable {

    /**
     * The background id to find the Token image.
     */
    protected int background;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the Token to have a Piece background.
     * A piece with the empty_piece background and no player.
     * (Connect Four)
     */
    public Token() {
        this.background = R.drawable.empty_piece;
    }

    /**
     * Set the Token to have a Card background.
     * A Card with a background id; look up and set the id.
     * (Matching Cards)
     *
     * @param backgroundId the id of the Card
     */
    public Token(int backgroundId) {
        String uri = "card_" + String.valueOf(backgroundId);
        try {
            Class res = R.drawable.class;
            Field field = res.getField(uri);
            this.background = field.getInt(null);
        } catch (Exception e) {
            Log.e("DrawableAccess", "Failed to get Card resource by id", e);
        }
        // In the Card Constructor make sure to init id
    }

    /**
     * Set the Token to have a Tile background.
     * A tile with a background id; look up and set the id.
     * Adapted from http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
     * (Sliding Tiles)
     *
     * @param backgroundId the id of the tile
     * @param blank the id of the blank (last) tile
     */
    public Token(int backgroundId, int blank) {
        if (backgroundId + 1 == blank) {
            background = R.drawable.tile_0;
        } else {
            String uri = "tile_" + String.valueOf(backgroundId + 1);
            try {
                Class res = R.drawable.class;
                Field field = res.getField(uri);
                background = field.getInt(null);
            } catch (Exception e) {
                Log.e("DrawableAccess", "Failed to get resource by id", e);
            }
        }
    }
}