package fall2018.csc2017.common;

import android.content.Context;
import android.widget.Toast;

class MovementController {

    private BoardManager boardManager = null;

    MovementController() {
    }

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position) {
        if (boardManager.gameFinished()) {
            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_LONG).show();
            }
        }
    }
}

