package fall2018.csc2017.common;

/*
 * This Class was provided as part of the starter code.
 */

import android.content.Context;
import android.widget.Toast;

class MovementController {

    private BoardManager boardManager = null;

    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position, boolean display) {
        if (!boardManager.gameFinished()) {
            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_LONG).show();
            }
        }
    }
}

