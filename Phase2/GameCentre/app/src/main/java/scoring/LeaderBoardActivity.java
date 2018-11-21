package scoring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.R;
import gamelauncher.IntentKeys;
import gamelauncher.MainActivity;
import gamelauncher.SlidingTileActivity;
import users.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;


public class LeaderBoardActivity extends AppCompatActivity {

    /**
     * The leaderBoard.
     */
    private LeaderBoard leaderBoard;

    /**
     * Save file for the leaderBoard.
     */
    private static final String SAVE_FILENAME = "save_leaderboard.ser";

    /**
     * A HashMap of user accounts, by  name.
     */
    private HashMap<String, User> userAccounts;

    /**
     * The name of the current user.
     */
    private User currentUser;


    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_);
        leaderBoard = new LeaderBoard();
        loadFromFile(SAVE_FILENAME);
        Intent intent = getIntent();
        String gameName = intent.getStringExtra(IntentKeys.GAME_TITLE_KEY);

        int score = intent.getIntExtra(IntentKeys.SCORE_KEY, 0);
        userAccounts = (HashMap<String, User>) intent.getSerializableExtra(IntentKeys.USERACCOUNTS_KEY);
        currentUser = (User) intent.getSerializableExtra(IntentKeys.CURRENTUSER_KEY);

        Score storageScore = new Score(currentUser.getName(), score);

        leaderBoard.updateScores(gameName, storageScore);
        displayLeaders(gameName);
        saveToFile(SAVE_FILENAME);
    }

    /**
     * Switch to the SlidingTileTitleActivity view.
     */
    private void switchToTitleActivity() {
        Intent tmp = new Intent(this, MainActivity.class);
        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        startActivity(tmp);
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void onResume() {
        super.onResume();
        loadFromFile(SAVE_FILENAME);

        Intent intent = getIntent();

        String gameName = intent.getStringExtra(IntentKeys.GAME_TITLE_KEY);

        displayLeaders(gameName);
        saveToFile(SAVE_FILENAME);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SAVE_FILENAME);
    }

    @Override
    public void onBackPressed(){
        switchToTitleActivity();
        finish();
    }

    /**
     * Write the score values and username to the TextView display.
     *
     * @param gameName The name of the game to display top scores
     */
    private void displayLeaders(String gameName) {
        TextView tvScores = findViewById(R.id.tv_scores);
        ArrayList<Score> tempScores = leaderBoard.getTopScores(gameName);
        ArrayList<Integer> tempScoreValues = new ArrayList<>();
        ArrayList<String> tempScoreUsers = new ArrayList<>();

        // the number of scores is always equal to the number of usernames
        int numScores = tempScoreValues.size();

        for (Score score: tempScores) {
            tempScoreValues.add(score.getValue());
            tempScoreUsers.add(score.getUsername());
        }

        // Build the display string
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= numScores; i++){
            // -1 means that slot hasn't been filled yet
            if (tempScoreValues.get(i - 1) == -1){
                break;
            }
            sb.append(i);
            sb.append(": ");
            sb.append(tempScoreValues.get(i - 1));
            sb.append(" by ");
            sb.append(tempScoreUsers.get(i - 1));
            sb.append("\n");
        }
        //set the tvScores
        tvScores.setText(sb.toString());
    }

    /**
     * Load the leaderBoard from the indicated file.
     *
     * @param fileName the name of the file
     */
    @SuppressWarnings({"SameParameterValue"})
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                leaderBoard = (LeaderBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the leaderBoard to the indicated file.
     *
     * @param fileName the name of the file
     */
    @SuppressWarnings({"SameParameterValue"})
    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(leaderBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
