package fall2018.csc2017.common;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;

import fall2018.csc2017.R;

public abstract class Token implements Serializable {
    int background;

    int getBackground() {
        return background;
    }

    // For Piece
    public Token() {
        this.background = R.drawable.empty_piece;
    }

    // For Card
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

    // For Tile
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
        // In the Tile Constructor make sure to init id
    }
}