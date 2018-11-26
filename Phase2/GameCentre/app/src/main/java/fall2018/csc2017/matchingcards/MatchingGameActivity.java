package fall2018.csc2017.matchingcards;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.GestureDetectGridView;
import fall2018.csc2017.common.CustomAdapter;
import fall2018.csc2017.common.SaveAndLoadGames;
import fall2018.csc2017.gamelauncher.MatchingFragment;
import fall2018.csc2017.scoring.LeaderBoard;
import fall2018.csc2017.scoring.Score;
import fall2018.csc2017.users.User;

/**
 * The game activity.
 */
public class MatchingGameActivity extends AppCompatActivity implements Observer, SaveAndLoadFiles, SaveAndLoadGames {

    /**
     * The MatchingBoardManager.
     */
    private MatchingBoardManager matchingBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> cardButtons;

    /**
     * The Map of all the Users by name.
     */
    private HashMap<String, User> userAccounts;

    /**
     * The current User.
     */
    private User currentUser;

    /**
     * Number of cards per side of the board.
     */
    private int difficulty;

    /**
     * Boolean tracking whether the game has been won.
     */
    private boolean gameWon;


    private GestureDetectGridView gridView;
    private static int columnWidth;
    private static int columnHeight;

    /**
     * The score of this Matching cards game.
     */
    private int score;


    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateCardButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
    }

    public Context getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        matchingBoardManager = (MatchingBoardManager) loadGameFromFile(MatchingFragment.TEMP_SAVE_FILENAME);
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        difficulty = matchingBoardManager.getDifficulty();

        createCardButtons();

        addUndoButtonListener();

        updateScore();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(difficulty);
        gridView.setBoardManager(matchingBoardManager);
        matchingBoardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls to our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / difficulty;
                        columnHeight = displayHeight / difficulty;
                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the cards.
     */
    private void createCardButtons() {
        MatchingBoard board = matchingBoardManager.getBoard();
        cardButtons = new ArrayList<>();
        for (int row = 0; row < difficulty; row++) {
            for (int col = 0; col < difficulty; col++) {
                Button tmp = new Button(getApplicationContext());
                tmp.setBackgroundResource(board.getCard(row, col).getBackground());
                this.cardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the cards.
     */
    private void updateCardButtons() {
        MatchingBoard matchingBoard = matchingBoardManager.getBoard();
        int nextPos = 0;
        for (Button b : cardButtons) {
            int row = nextPos / difficulty;
            int col = nextPos % difficulty;
            b.setBackgroundResource(matchingBoard.getCard(row, col).getBackground());
            nextPos++;
        }
        // Updated pictures in event of a tap
        // Whether to tap to the front or the back
    }

    /**
     * The listener for the undo button.
     */
    private void addUndoButtonListener() {
        final Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(view -> {
                matchingBoardManager.undoMove();
        });
    }

    /**
     * Display the score as you play the game.
     */
    private void updateScore() {
        score = matchingBoardManager.generateScore();
        TextView curScore = findViewById(R.id.curScore);
        curScore.setText(String.valueOf(score));
    }

    @Override
    public void update(Observable o, Object arg) {
        updateScore();
        // Automatic save every 5 moves
        int moves = matchingBoardManager.getNumMoves() % 5;
        if (moves == 0 && !gameWon) {
            currentUser.getSaves().put(MatchingFragment.GAME_TITLE, matchingBoardManager);
            saveUserAccounts(userAccounts);
        }
        if (matchingBoardManager.gameFinished()) {
            gameWon = true;
            createToast("You Win!");
            LeaderBoard leaderBoard = loadLeaderBoard();
            leaderBoard.updateScores("Matching Cards", new Score(currentUser.getName(), score));
            saveLeaderBoard(leaderBoard);
        }
        display();
    }

    @Override
    public void onBackPressed() {
        switchToMatchingTitleActivity();
    }

    /**
     * Switch to the title screen. Only to be called when back pressed.
     */
    private void switchToMatchingTitleActivity() {

        writeNewValues(currentUser, MatchingFragment.GAME_TITLE, matchingBoardManager);
        saveUserAccounts(userAccounts);
        if (!gameWon) {
            createToast("Saved");
        } else {
            createToast("Saved Wiped");
        }
        finish();
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}