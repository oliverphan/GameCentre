package fall2018.csc2017.common;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.common.BoardManager;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (!boardManager.gameFinished()) {
            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_LONG).show();
            }
        }
    }
}

