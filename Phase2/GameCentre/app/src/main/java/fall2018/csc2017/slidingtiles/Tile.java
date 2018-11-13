package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The HashMap of Tile Ids and user provided backgrounds.
     */
    @SuppressWarnings("all")
    private static HashMap<Integer, Bitmap> userTiles = new HashMap<>();
    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the slice of the user image created for this Tile.
     *
     * @return Bitmap slice of user image
     */
    Bitmap getUserImage() {
        return userTiles.get(this.id);
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    Tile(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     * Adapted from http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
     *
     * @param backgroundId the id of the tile
     */
    Tile(int backgroundId) {
        id = backgroundId;
        String uri = "tile_" + String.valueOf(backgroundId);
        try {
            Class res = R.drawable.class;
            Field field = res.getField(uri);
            background = field.getInt(null);
        } catch (Exception e) {
            Log.e("DrawableAccess", "Failed to get resource by id", e);
        }
    }

    /**
     * Slices and stores a user provided image to use on tiles
     *
     * @param image User provided image
     * @param diff  Difficulty of the game
     */
    void createUserTiles(Bitmap image, int diff) {
        int width = 247;
        int height = 319;
        int row = (this.id - 1) / diff;
        int col = (this.id - 1) % diff;
        if (this.id == 0) {
            userTiles.put(this.id, Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
        } else {
            userTiles.put(this.id, Bitmap.createBitmap(image, width * col, height * row, width, height));
        }
    }


    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
