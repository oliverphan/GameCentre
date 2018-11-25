package fall2018.csc2017;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.slidingtiles.BoardManager;
import gamelauncher.LoginActivity;
import users.User;

/**
 * An Interface for:
 * Loading userAccounts, the current User, the boardManager
 * Saving userAccounts, the current User, the boardManager
 */
 public interface SaveAndLoad {

   /**
    * Load the user accounts
    * @return HashMap<String, User> m, the Map of the user accounts.
    */
   @SuppressWarnings("unchecked")
    default HashMap<String, User> loadUserAccounts()  {
      try {
          InputStream inputStream = getActivity().openFileInput(LoginActivity.ACCOUNTS_SAVE_FILENAME);
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
     * @return String s, the name of the current user.
     */
    default String loadCurrentUsername() {
       try {
           InputStream inputStream = getActivity().openFileInput(LoginActivity.USER_SAVE_FILENAME);
           if (inputStream != null){
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
     * Save the Hashmap of user accounts to file.
     * @param accounts the map of user accounts to be written.
     */
    default void saveUserAccounts(HashMap<String, User> accounts) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(LoginActivity.ACCOUNTS_SAVE_FILENAME, Context.MODE_PRIVATE));
            outputStream.writeObject(accounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("save user accounts", "File write failed: " + e.toString());
        }
    }
    /**
     * Save the current username to file.
     * @param username the username to be written.
     */
    default void saveCurrentUsername(String username) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(LoginActivity.USER_SAVE_FILENAME, Context.MODE_PRIVATE));
            outputStream.writeObject(username);
            outputStream.close();
        } catch (IOException e) {
            Log.e("save current username", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the BoardManager for the corresponding user and game.
     * @param username the username of the user
     * @param game the name of the game
     * @return (BoardManager) the BoardManager for the corresponding user and game
     */
    default Object loadBoardManager(String username, String game){
        try {
            InputStream inputStream = getActivity().openFileInput(LoginActivity.ACCOUNTS_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                HashMap<String, User> userAccounts = (HashMap<String, User>) input.readObject();
                BoardManager result = (BoardManager) userAccounts.get(username).getSaves().get(game);
                inputStream.close();
                return result;
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            Log.e("load user accounts", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("load user accounts", "File contained unexpected data type: " + e.toString());
        }
        return null;
        }

    /**
     * Save the BoardManager of user accounts to file.
     * @param username the username of the user
     * @param game the name of the game
     * @param boardManager the BoardManager to be saved.
     */

    default void saveBoardManager(String username, String game, BoardManager boardManager){

        HashMap<String, User> userAccounts = loadUserAccounts();
        User u = userAccounts.get(username);
        u.writeGame(game, boardManager);
        saveUserAccounts(userAccounts);

    }



    Context getActivity();
}
