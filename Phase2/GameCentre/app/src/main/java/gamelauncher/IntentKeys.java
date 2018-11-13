package gamelauncher;

public interface IntentKeys {
    /**
     * A key to get the current user in different activities.
     */
    String CURRENTUSER_KEY = "username";

    /**
     * The key to get the name of the game from a game activity.
     */
    String GAME_TITLE_KEY = "gametitle";

    /**
     * The key to get the difficulty from a game activity.
     */
    String DIFFICULTY_KEY = "difficulty";

    /**
     * The key to get a board manager from a sliding tile game activity.
     */
    String BOARDMANAGER_KEY = "boardmanager";

    /**
     * The key to get the score from a game activity.
     */
    String SCORE_KEY = "score";

    /**
     * The key to get the HashMap of user accounts from the login activity.
     */
    String USERACCOUNTS_KEY = "useraccounts";

    /**
     * The key to get a boolean telling whether the last Activity was the game activity.
     */
    String GAMECOMPLETE_KEY = "gamecomplete";

    /**
     * The key to get a boolean telling whether the game was won.
     */
    String GAMEWON_KEY = "gamewonw";

    /**
     * The key to get a boolean telling whether the game will be loaded.
     */
    String TOLOAD_KEY = "toload";

    String USERTILES_KEY = "usertiles";
}
