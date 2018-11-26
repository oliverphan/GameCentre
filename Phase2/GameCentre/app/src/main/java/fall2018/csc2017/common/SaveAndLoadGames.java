package fall2018.csc2017.common;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * An Interface for:
 * Loading the Game (Board Manager) from file
 * Writing the game to the User object
 */
public interface SaveAndLoadGames {

    /**
     * Save the boardManager for passing game around.
     *
     * @param fileName the name of the file
     */
    default void saveGameToFile(String fileName, BoardManager boardManager) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    Context getActivity();
}
