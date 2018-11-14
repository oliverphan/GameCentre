package gamelauncher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
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
     * The current logged in user.
     */
    private User currentUser;

    /**
     * An integer indicating what complexity level game is to be played at.
     * Passed as 3, 4, or 5.
     */
    private int complexity;

    /**
     * The score of the last played sliding tile game.
     */
    private int score;

    /**
     * A boolean tracking whether or not the load button was pressed.
     */
    private boolean toLoad;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelaunchcentre);

        Intent intent = getIntent();
        if (userAccounts == null) {
            userAccounts = new HashMap<>();
        }

        toLoad = false;
        userAccounts = (HashMap<String, User>) intent.getSerializableExtra(IntentKeys.USERACCOUNTS_KEY);
        currentUser = (User) intent.getSerializableExtra(IntentKeys.CURRENTUSER_KEY);

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
                toLoad = true;
                boolean saveFileExists = currentUser.getSaves().containsKey(GAME_TITLE);
                if (saveFileExists) {
                    makeToastLoadedText();
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
                complexity = 3;
                toLoad = false;
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
                complexity = 4;
                toLoad = false;
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
                complexity = 5;
                toLoad = false;
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
        tmp.putExtra(IntentKeys.DIFFICULTY_KEY, complexity);
        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        tmp.putExtra(IntentKeys.TOLOAD_KEY, toLoad);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard view
     */
    private void switchToLeaderBoardActivity() {
        Intent tmp = new Intent(this, LeaderBoardActivity.class);
        tmp.putExtra(IntentKeys.GAME_TITLE_KEY, GAME_TITLE);
        tmp.putExtra(IntentKeys.SCORE_KEY, score);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
        startActivity(tmp);
    }

    /**
     * Switch to the StartingActivity view
     */
    private void switchToGameCentreLoginActivity() {
        Intent tmp = new Intent(this, LoginActivity.class);
        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        startActivity(tmp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onResume() {
        super.onResume();
        toLoad = false;
        Intent intent = getIntent();
        BoardManager save = (BoardManager) intent.getSerializableExtra(IntentKeys.BOARDMANAGER_KEY);
        boolean gameCompleted = intent.getBooleanExtra(IntentKeys.GAMECOMPLETE_KEY, false);
        boolean gameWon = intent.getBooleanExtra(IntentKeys.GAMEWON_KEY, false);
        userAccounts = (HashMap<String, User>) intent.getSerializableExtra(IntentKeys.USERACCOUNTS_KEY);
        currentUser = (User) intent.getSerializableExtra(IntentKeys.CURRENTUSER_KEY);
        if (gameCompleted) {
            if (gameWon) {
                score = intent.getIntExtra(IntentKeys.SCORE_KEY, 0);
                currentUser.getScores().put(GAME_TITLE, score);
            } else {
                currentUser.getSaves().put(GAME_TITLE, save);
            }
        }
        saveToFile(LoginActivity.SAVE_FILENAME);
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
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }
}
