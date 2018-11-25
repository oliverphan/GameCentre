package fall2018.csc2017.Memory;

import android.content.Context;
import android.widget.Toast;



public class MemoryMovementController {

    private MemoryBoardManager memoryBoardManager = null;

    public MemoryMovementController() {
    }

    public void setMemoryBoardManager(MemoryBoardManager boardManager) {
        this.memoryBoardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (!memoryBoardManager.gameOver()) {
            if (memoryBoardManager.isValidTap(position)) {
                memoryBoardManager.touchMove(position);
            } else {
                Toast.makeText(context, "Invalid Tap", Toast.LENGTH_LONG).show();
            }
        }
    }
}

