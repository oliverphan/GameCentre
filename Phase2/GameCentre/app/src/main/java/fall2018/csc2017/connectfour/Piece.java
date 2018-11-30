package fall2018.csc2017.connectfour;

import fall2018.csc2017.R;
import fall2018.csc2017.common.Token;

/**
 * A Piece in a Connect Four game.
 */
public class Piece extends Token {

    /**
     * Player that the piece belongs to.
     */
    private int player;


    /**
     * A piece with the empty_piece background and no player.
     */
    public Piece() {
        super();
        this.player = 0;
    }

    /**
     * Returns the player that this Piece belongs to.
     *
     * @return player that owns this piece (1: User, 2: AI, 0: empty)
     */
    int getPlayer() {
        return this.player;
    }

    /**
     * Set a new player that owns this Piece
     *
     * @param player integer value to designate the player that owns this Piece
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
