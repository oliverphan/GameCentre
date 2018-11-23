package scoring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.R;
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
//        setContentView(R.layout.activity_scoreboard_);
//        leaderBoard = new LeaderBoard();
//        loadFromFile(SAVE_FILENAME);
//        Intent intent = getIntent();
////        String gameName = intent.getStringExtra(IntentKeys.GAME_TITLE_KEY);
////
////        int score = intent.getIntExtra(IntentKeys.SCORE_KEY, 0);
////        userAccounts = (HashMap<String, User>) intent.getSerializableExtra(IntentKeys.USERACCOUNTS_KEY);
////        currentUser = (User) intent.getSerializableExtra(IntentKeys.CURRENTUSER_KEY);
//
//        Score storageScore = new Score(currentUser.getName(), score);
//
//        leaderBoard.updateScores(gameName, storageScore);
//        displayLeaders(gameName);
//        saveToFile(SAVE_FILENAME);
    }

    /**
     * Switch to the SlidingTileTitleActivity view.
     */
    private void switchToTitleActivity() {
        Intent tmp = new Intent(this, SlidingTileActivity.class);
//        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
//        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        startActivity(tmp);
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void onResume() {
        super.onResume();
//        loadFromFile(SAVE_FILENAME);
//
//        Intent intent = getIntent();
//
//        String gameName = intent.getStringExtra(IntentKeys.GAME_TITLE_KEY);
//
//        displayLeaders(gameName);
//        saveToFile(SAVE_FILENAME);
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

        // A public SharedPreferences interface.
        SharedPreferences prefs = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = prefs.edit();
        ArrayList<Score> tempScores = leaderBoard.getTopScores(gameName);

        Integer topScore1 = tempScores.get(0).getValue();
        Integer topScore2 = tempScores.get(1).getValue();
        Integer topScore3 = tempScores.get(2).getValue();

        String user1 = tempScores.get(0).getUsername();
        String user2 = tempScores.get(1).getUsername();
        String user3 = tempScores.get(2).getUsername();

        // Load top 3 scores
        editor.putInt("topScore1", topScore1);
        editor.putInt("topScore2", topScore2);
        editor.putInt("topScore3", topScore3);
        editor.apply();

        // Load users of top 3 scores
        editor.putString("user1", user1);
        editor.putString("user2", user2);
        editor.putString("user3", user3);
        editor.apply();

        //set the tvScores
        String tvScoresText = ("1: " + topScore1 + " by " + user1 + "\n"
                + "2: " + topScore2 + " by " + user2 + "\n" + "3: " + topScore3 + " by " + user3);
        tvScores.setText(tvScoresText);
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
