package fall2018.csc2017.connectFour;

import android.content.Context;
import android.widget.Toast;


public class FourMovementController {

    private FourBoardManager boardManager = null;

    public FourMovementController() {
    }

    public void setBoardManager(FourBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (!boardManager.gameFinished()) {
            if (boardManager.isValidTap(position)) {
                boardManager.makeMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_LONG).show();
            }
        }
    }
}


