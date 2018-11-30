package fall2018.csc2017.common;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.scoring.LeaderBoard;
import fall2018.csc2017.scoring.Score;
import fall2018.csc2017.users.User;

/**
 * An Interface for:
 * Loading userAccounts, the current User, the boardManager
 * Saving userAccounts, the current User, the boardManager
 */
public interface SaveAndLoadFiles {

    /**
     * The save file for userAccounts.
     */
    String ACCOUNTS_SAVE_FILENAME = "accounts_save_file.ser";

    /**
     * The save file for currentUser.
     */
    String USER_SAVE_FILENAME = "user_save_file.ser";

    /**
     * Save file for the leaderBoard.
     */
    String LEADERBOARD_SAVE_FILENAME = "leaderboard_save.ser";

    /**
     * Return the user accounts from file.
     *
     * @return a Map of the user accounts, loaded from file
     */
    @SuppressWarnings("unchecked")
    default HashMap<String, User> loadUserAccounts() {
        try {
            InputStream inputStream = getActivity().openFileInput(ACCOUNTS_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                HashMap<String, User> result = (HashMap<String, User>) input.readObject();
                inputStream.close();
                return result;
            }
        } catch (FileNotFoundException e) {
            Log.e("load user accounts", "File does not exist: " + e.toString());
        } catch (IOException e) {
            Log.e("load user accounts", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("load user accounts", "File contained unexpected data type: "
                    + e.toString());
        }
        return new HashMap<>();
    }

    /**
     * Return a String representation of the current users name.
     *
     * @return String s, the name of the current user
     */
    default String loadCurrentUsername() {
        try {
            InputStream inputStream = getActivity().openFileInput(USER_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                String result = (String) input.readObject();
                inputStream.close();
                return result;
            }
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            Log.e("load current username", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("load current username", "File contained unexpected data type: "
                    + e.toString());
        }
        return "";
    }

    /**
     * Save user accounts to file.
     *
     * @param accounts the map of user accounts to be written to the file
     */
    default void saveUserAccounts(Map<String, User> accounts) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(ACCOUNTS_SAVE_FILENAME, Context.MODE_PRIVATE));
            outputStream.writeObject(accounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("save user accounts", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the current username to file.
     *
     * @param username the username to be written to the file
     */
    default void saveCurrentUsername(String username) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(USER_SAVE_FILENAME, Context.MODE_PRIVATE));
            outputStream.writeObject(username);
            outputStream.close();
        } catch (IOException e) {
            Log.e("save current username", "File write failed: " + e.toString());
        }
    }

    /**
     * Return a LeaderBoard from file.
     *
     * @return return a LeaderBoard from file
     */
    default LeaderBoard loadLeaderBoard() {
        try {
            InputStream inputStream = getActivity().openFileInput(LEADERBOARD_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                LeaderBoard leaderBoard = (LeaderBoard) input.readObject();
                inputStream.close();
                return leaderBoard;
            }
        } catch (FileNotFoundException e) {
            return new LeaderBoard();
        } catch (IOException e) {
            Log.e("leaderboard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("leaderboard activity", "File contained unexpected data type: "
                    + e.toString());
        }
        return null;
    }

    /**
     * Save a LeaderBoard to file.
     *
     * @param leaderBoard the LeaderBoard to be written to a file
     */
    default void saveLeaderBoard(LeaderBoard leaderBoard) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(LEADERBOARD_SAVE_FILENAME, Context.MODE_PRIVATE));
            outputStream.writeObject(leaderBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Update the LeaderBoard once a game is finished.
     *
     * @param gameName the name of the game to be possibly updated
     * @param score the Score object tied to the User that generated it
     */
    default void updateLeaderBoard(String gameName, Score score) {
        LeaderBoard tmp = loadLeaderBoard();
        tmp.updateScores(gameName, score);
        saveLeaderBoard(tmp);
    }

    Context getActivity();
}
