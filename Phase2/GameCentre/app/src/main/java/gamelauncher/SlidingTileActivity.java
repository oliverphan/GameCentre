package gamelauncher;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.BoardManager;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTileGameActivity;
import scoring.LeaderBoardActivity;
import users.User;

import android.widget.TextView;

public class SlidingTileActivity extends Fragment {
    /**
     * The tag of the fragment
     */
    public static final String TAG = "SlidingTiles";

    /**
     * The name of the game.
     */
    public static final String GAME_TITLE = "Sliding Tiles";

    // /**
    //  * A HashMap of all the Users created. The key is the username, the value is the User object.
    //  */
    // private HashMap<String, User> userAccounts;

    /**
     * The main save file.
     */
     // Save a board manager
    public static final String TEMP_SAVE_FILENAME = "tmp_save_file.ser";

     /**
      * The current logged in user.
      */
     private User currentUser;

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_slidingtiles, container, false);
        Bundle args = getArguments();
        loadUserFromFile();
//        addLeaderBoardListener(view);
        addLaunchGame3Listener(view);
        addLaunchGame4Listener(view);
        addLaunchGame5Listener(view);
        addLoadButtonListener(view);
        return view;
    }

//    /**
//     * Display the Current user's username.
//     */
//    private void displayCurrentUser(View view) {
//        TextView displayName = view.findViewById(R.id.tv_CurrentUser);
//        String temp = "Logged in as " + currentName;
//        displayName.setText(temp);
//    }

    // /**
    //  * Display the Current user's score.
    //  */
    // private void displayCurrentScore() {
    //     TextView displayScore = findViewById(R.id.tv_currentUserScore);
    //     Integer value = currentUser.getScores().get(GAME_TITLE);
    //     String valueText = "Your High Score: " + value.toString();
    //     displayScore.setText(valueText);
    // }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener(View view) {
        Button loadButton = view.findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean saveFileExists = currentUser.getSaves().containsKey(GAME_TITLE);
                if (saveFileExists) {
                    makeToastLoadedText();
                    boardManager = (BoardManager) currentUser.getSaves().get(GAME_TITLE);
                    switchToSlidingTileGameActivity();
                } else {
                    Toast.makeText(getContext(), "No file exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    /**
//     * Activate the log out button.
//     */
//    private void addLogOutButtonListener(View view) {
//        Button logOutButton = view.findViewById(R.id.LogOutButton);
//        logOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchToGameCentreLoginActivity();
//            }
//        });
//    }

    /**
     * Activate the Sliding Tiles 3x3 button.
     */
    private void addLaunchGame3Listener(View view) {
        Button launchGame3Button = view.findViewById(R.id.LaunchGame3);
        launchGame3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(3);
                switchToSlidingTileGameActivity();
            }
        });
    }

    /**
     * Activate the Sliding Tiles 4x4 button.
     */
    private void addLaunchGame4Listener(View view) {
        Button launchGame4Button = view.findViewById(R.id.LaunchGame4);
        launchGame4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(4);
                switchToSlidingTileGameActivity();
            }
        });
    }

    /**
     * Activate the Sliding Tiles 5x5 button.
     */
    private void addLaunchGame5Listener(View view) {
        Button launchGame5Button = view.findViewById(R.id.LaunchGame5);
        launchGame5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5);
                switchToSlidingTileGameActivity();
            }
        });

    }
//
//    /**
//     * Activate the scoreboard of top scores button.
//     */
//    private void addLeaderBoardListener(View view) {
//        Button scoreBoardButton = view.findViewById(R.id.LeaderBoardButton);
//        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchToLeaderBoardActivity();
//
//            }
//        });
//
//    }

    /**
     * Switch to the SlidingTilesGameActivity view
     */
    private void switchToSlidingTileGameActivity() {
        Intent tmp = new Intent(getActivity(), SlidingTileGameActivity.class);
        tmp.putExtra("currentUser", currentUser.getName());
        saveGameToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard view
     */
    private void switchToLeaderBoardActivity() {
        Intent tmp = new Intent(getActivity(), LeaderBoardActivity.class);
        tmp.putExtra(IntentKeys.GAME_TITLE_KEY, GAME_TITLE);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        startActivity(tmp);
    }

    /**
     * Switch to the StartingActivity view
     */
    private void switchToGameCentreLoginActivity() {
        Intent tmp = new Intent(getActivity(), LoginActivity.class);
        startActivity(tmp);
    }

    @Override
    // Probably not needed
    public void onResume() {
        super.onResume();
        loadUserFromFile();
    }

    // /**
    //  * Save the user account info to fileName.
    //  *
    //  * @param fileName the name of the file
    //  */
    // public void saveToFile(String fileName) {
    //     try {
    //         ObjectOutputStream outputStream = new ObjectOutputStream(
    //                 this.openFileOutput(fileName, MODE_PRIVATE));
    //         outputStream.writeObject(userAccounts);
    //         outputStream.close();
    //     } catch (IOException e) {
    //         Log.e("Exception", "File write failed: " + e.toString());
    //     }
    // }

    /**
     * Save the boardManager for passing game around.
     *
     * @param fileName the name of the file
     */
    public void saveGameToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(fileName, getContext().MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUserFromFile() {
        try {
            InputStream inputStream = getActivity().openFileInput(LoginActivity.USER_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                currentUser = (User) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("load game activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("load game activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("load game activity", "File contained unexpected data type: " + e.toString());
        }
    }

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "To logout press LOGOUT", Toast.LENGTH_SHORT).show();
//
//    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(getContext(), "Loaded Game", Toast.LENGTH_SHORT).show();
    }
}
