package gamelauncher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SlidingTileTitleActivity extends AppCompatActivity {

    /**
     * The name of the game.
     */
    public static final String GAME_TITLE = "Sliding Tiles";
    /**
     * A HashMap of all the Users created. The key is the username, the value is the User object.
     */
    private HashMap<String, User> userAccounts;

    /**
     * The main save file.
     */
    public static final String TEMP_SAVE_FILENAME = "tmp_save_file.ser";

    /**
     * The current logged in user.
     */
    private User currentUser;

    /**
     * The score of the last played sliding tile game.
     */
    private int score;


    /**
     * The board manager.
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (User) getIntent().getSerializableExtra("currentUser");
        setContentView(R.layout.activity_gamelaunchcentre);
        loadUserFromFile();
        addLogOutButtonListener();
        displayCurrentUser();
        displayCurrentScore();
        addLeaderBoardListener();
        addLaunchGame3Listener();
        addLaunchGame4Listener();
        addLaunchGame5Listener();
        addLoadButtonListener();
    }

    /**
     * Display the Current user's username.
     */
    private void displayCurrentUser() {
        TextView displayName = findViewById(R.id.tv_CurrentUser);
        String temp = "Logged in as " + currentUser.getName();
        displayName.setText(temp);
    }

    /**
     * Display the Current user's score.
     */
    private void displayCurrentScore() {
        TextView displayScore = findViewById(R.id.tv_currentUserScore);
        Integer value = currentUser.getScores().get(GAME_TITLE);
        String valueText = "Your High Score: " + value.toString();
        displayScore.setText(valueText);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean saveFileExists = currentUser.getSaves().containsKey(GAME_TITLE);
                if (saveFileExists) {
                    makeToastLoadedText();
                    boardManager = (BoardManager) currentUser.getSaves().get(GAME_TITLE);
                    switchToSlidingTileGameActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "No file exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Activate the log out button.
     */
    private void addLogOutButtonListener() {
        Button logOutButton = findViewById(R.id.LogOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentreLoginActivity();
            }
        });
    }

    /**
     * Activate the Sliding Tiles 3x3 button.
     */
    private void addLaunchGame3Listener() {
        Button launchGame3Button = findViewById(R.id.LaunchGame3);
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
    private void addLaunchGame4Listener() {
        Button launchGame4Button = findViewById(R.id.LaunchGame4);
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
    private void addLaunchGame5Listener() {
        Button launchGame5Button = findViewById(R.id.LaunchGame5);
        launchGame5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5);
                switchToSlidingTileGameActivity();
            }
        });

    }

    /**
     * Activate the scoreboard of top scores button.
     */
    private void addLeaderBoardListener() {
        Button scoreBoardButton = findViewById(R.id.LeaderBoardButton);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLeaderBoardActivity();

            }
        });

    }

    /**
     * Switch to the SlidingTilesGameActivity view
     */
    private void switchToSlidingTileGameActivity() {
        Intent tmp = new Intent(this, SlidingTileGameActivity.class);
        tmp.putExtra("currentUser", currentUser.getName());
        saveToFile(LoginActivity.SAVE_FILENAME);
        saveGameToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard view
     */
    private void switchToLeaderBoardActivity() {
        Intent tmp = new Intent(this, LeaderBoardActivity.class);
        tmp.putExtra(IntentKeys.GAME_TITLE_KEY, GAME_TITLE);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        startActivity(tmp);
        finish();
    }

    /**
     * Switch to the StartingActivity view
     */
    private void switchToGameCentreLoginActivity() {
        Intent tmp = new Intent(this, LoginActivity.class);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserFromFile();
        score = currentUser.getScores().get(GAME_TITLE);
        displayCurrentScore();
        displayCurrentUser();
    }

    /**
     * Save the user account info to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the boardManager for passing game around.
     *
     * @param fileName the name of the file
     */
    public void saveGameToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUserFromFile() {
        try {
            InputStream inputStream = this.openFileInput(LoginActivity.SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userAccounts = (HashMap<String, User>) input.readObject();
                currentUser = userAccounts.get(currentUser.getName());
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "To logout press LOGOUT", Toast.LENGTH_SHORT).show();

    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }
}
