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

import scoring.LeaderBoard;
import users.User;

/**
 * An Interface for:
 * Loading userAccounts, the current User, the boardManager
 * Saving userAccounts, the current User, the boardManager
 */
public interface SaveAndLoad {

    /**
     * The save file for userAccounts.
     */
    public static final String ACCOUNTS_SAVE_FILENAME = "accounts_save_file.ser";

    /**
     * The save file for currentUser.
     */
    public static final String USER_SAVE_FILENAME = "user_save_file.ser";

    /**
     * Save file for the leaderBoard.
     */
    static final String LEADERBOARD_SAVE_FILENAME = "leaderboard_save.ser";

    /**
     * Load the user accounts
     *
     * @return HashMap<String, User> m, the Map of the user accounts.
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
            return new HashMap<>();
        } catch (IOException e) {
            Log.e("load user accounts", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("load user accounts", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Load the current username
     *
     * @return String s, the name of the current user.
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
            Log.e("load current username", "File contained unexpected data type: " + e.toString());
        }
        return "";
    }

    /**
     * Save the map of user accounts to file.
     *
     * @param accounts the map of user accounts to be written.
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
     * @param username the username to be written.
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


    @SuppressWarnings({"SameParameterValue"})
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
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

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

    Context getActivity();
}
