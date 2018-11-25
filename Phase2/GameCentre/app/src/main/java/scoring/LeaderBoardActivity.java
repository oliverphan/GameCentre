package scoring;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.SaveAndLoad;
import fall2018.csc2017.slidingtiles.R;
import gamelauncher.SlidingTileActivity;
import users.User;

//TODO: Make 3 fragments, one for each game, and put in some of the code here for each one.

public class LeaderBoardActivity extends AppCompatActivity implements SaveAndLoad {

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
    }

    /**
     * Switch to the SlidingTileTitleActivity view.
     */
    private void switchToTitleActivity() {
        Intent tmp = new Intent(this, SlidingTileActivity.class);
        startActivity(tmp);
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void onResume() {
        super.onResume();
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
        int numScores = tempScoreValues.size(); //numScores == the number of users too
        for (Score score: tempScores) {
            tempScoreValues.add(score.getValue());
            tempScoreUsers.add(score.getUsername());
        }
        StringBuilder sb = new StringBuilder(); // Build the display string
        for (int i = 1; i <= numScores; i++){
            if (tempScoreValues.get(i - 1) == -1){ // -1 means that slot hasn't been filled yet
                break;
            }
            sb.append(i);
            sb.append(": ");
            sb.append(tempScoreValues.get(i - 1));
            sb.append(" by ");
            sb.append(tempScoreUsers.get(i - 1));
            sb.append("\n");
        }
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

    public Context getActivity(){
        return this;
    }
}
