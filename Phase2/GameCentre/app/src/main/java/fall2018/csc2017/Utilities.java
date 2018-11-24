/**
 * An Interface for:
 * Loading userAccounts, the current User, the boardManager
 * Saving userAccounts, the current User, the boardManager
 */
 public Interface Utilities {

   /**
    * Load the UserAccounts
    * @return HashMap<String, User> the Map of the userAccounts.
    */
    default Map<String, User> loadUserAccounts() throws FileNotFoundException {
      try {
          InputStream inputStream = getActivity().openFileInput(LoginActivity.USER_SAVE_FILENAME);
          if (inputStream != null) {
              ObjectInputStream input = new ObjectInputStream(inputStream);
              inputStream.close();
              return (HashMap<String, User>) input.readObject()
          }
      } catch (IOException e) {
          Log.e("load game activity", "Can not read file: " + e.toString());
      } catch (ClassNotFoundException e) {
          Log.e("load game activity", "File contained unexpected data type: " + e.toString());
      }
    }
 }
