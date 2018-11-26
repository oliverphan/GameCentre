package fall2018.csc2017.common;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.users.User;

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

    /**
     * Load the game from the file.
     *
     * @param fileName the name of the file to load the game from
     */
    default BoardManager loadGameFromFile(String fileName) {
        try {
            InputStream inputStream = getActivity().openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                BoardManager result = (BoardManager) input.readObject();
                inputStream.close();
                return result;
            }
        } catch (FileNotFoundException e) {
            Log.e("Game activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Game activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Game activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Store the new score and delete the old save in the User if the game is won.
     * If game hasn't been won, store the most recent BoardManager to the User.
     */
    default void writeNewValues(User currentUser, String gameName, BoardManager boardManager) {
        if (!boardManager.gameFinished()) {
            currentUser.writeGame(gameName, boardManager);
        } else {
//            THIS MIGHT NOT WORK
            currentUser.setNewScore(gameName, boardManager.generateScore());
            currentUser.deleteSave(gameName);
        }
    }

    Context getActivity();
}
