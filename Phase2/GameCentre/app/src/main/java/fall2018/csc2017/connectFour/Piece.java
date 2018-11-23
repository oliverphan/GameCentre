package fall2018.csc2017.connectFour;

import java.io.Serializable;

import fall2018.csc2017.slidingtiles.R;

/**
 * The Piece in a Connect Four game.
 */
public class Piece implements Serializable {
    /**
     * Player that the piece belongs to.
     */
    private int player;

    /**
     * The background id of the Piece
     */
    private int background;

    /**
     * A piece with the empty_piece background and no player.
     */
    Piece() {
        this.player = 0;
        this.background = R.drawable.empty_piece;
    }

    /**
     * Getter to return the player that this Piece belongs to.
     *
     * @return player that owns this piece (1: User, 2: AI, 0: empty)
     */
    int getPlayer() {
        return this.player;
    }

    /**
     * Return the background id of this piece
     *
     * @return the background id
     */
    int getBackground() {
        return this.background;
    }

    /**
     * Set a new player that owns this Piece
     *
     * @param player int to designate the player that owns this Piece
     */
    void setPlayer(int player) {
        this.player = player;
        if (player == 0) {
            this.background = R.drawable.empty_piece;
        } else {
            this.background = (player == 1) ? R.drawable.piece_1 : R.drawable.piece_2;
        }
    }
}
