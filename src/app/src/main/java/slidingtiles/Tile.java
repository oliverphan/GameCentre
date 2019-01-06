package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.common.Token;


/**
 * A Tile in a Sliding Tiles puzzle.
 */
public class Tile extends Token implements Comparable<Tile> {

    /**
     * The HashMap of Tile Ids and slices of a User provided background.
     */
    private static Map<Integer, Bitmap> userTiles = new HashMap<>();

    /**
     * The unique id.
     */
    private int id;


    /**
     * A tile with a background id; looks up and sets the id.
     * Adapted from
     * http://daniel-codes.blogspot.com/2009/12/dynamically-retrieving-resources-in.html
     *
     * @param backgroundId the id of the tile
     * @param blank        the id of the blank (last) tile
     */
    Tile(int backgroundId, int blank) {
        super(backgroundId, blank);
        id = backgroundId + 1;
    }

    /**
     * Returns the id value of this Tile.
     *
     * @return the this Tiles id
     */
    public int getId() {
        return id;
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
     * Slices and stores a user provided image to use as Tile images.
     *
     * @param image the user provided image
     * @param diff  the difficulty of the current game
     */
    void createUserTiles(Bitmap image, int diff) {
        int width = 247;
        int height = 319;
        int row = (this.id - 1) / diff;
        int col = (this.id - 1) % diff;
        if (this.id == (diff * diff)) {
            userTiles.put(this.id, Bitmap.createBitmap(image,
                    (diff - 1) * width, (diff - 1) * height, width, height));
        } else {
            userTiles.put(this.id, Bitmap.createBitmap(image,
                    width * col, height * row, width, height));
        }
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
