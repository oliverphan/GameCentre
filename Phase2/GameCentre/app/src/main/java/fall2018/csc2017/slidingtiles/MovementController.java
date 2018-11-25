package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    private SlidingBoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(SlidingBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (!boardManager.puzzleSolved()) {
            if (boardManager.isValidTap(position)) {
                boardManager.touchMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_LONG).show();
            }
        }
    }
}

