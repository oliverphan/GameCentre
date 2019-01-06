package fall2018.csc2017.matchingcards;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.common.CustomAdapter;
import fall2018.csc2017.common.GestureDetectGridView;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.SaveAndLoadGames;
import fall2018.csc2017.gamelauncher.MatchingFragment;
import fall2018.csc2017.scoring.Score;
import fall2018.csc2017.users.User;

/**
 * The game activity.
 */
public class MatchingGameActivity extends AppCompatActivity implements Observer,
        SaveAndLoadFiles, SaveAndLoadGames {

    /**
     * The MatchingBoardManager.
     */
    private MatchingBoardManager matchingBoardManager;

    /**
     * The buttons to display.
     */
    private List<Button> cardButtons;

    /**
     * The Map of all the Users by name.
     */
    private Map<String, User> userAccounts;

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


    /**
     * The grid for the game activity.
     */
    private GestureDetectGridView gridView;

    /**
     * Width of each card.
     */
    private static int columnWidth;

    /**
     * Height of each card.
     */
    private static int columnHeight;

    /**
     * The score of this Matching cards game.
     */
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchingcardsgame);
        matchingBoardManager = (MatchingBoardManager) loadGameFromFile(
                MatchingFragment.TEMP_SAVE_FILENAME);
        updateScore();
        initializeUsers();
        difficulty = matchingBoardManager.getDifficulty();
        createCardButtons();
        addUndoButtonListener();
        initializeGrid();
    }

    /**
     * A helper method to initialize the user information.
     */
    private void initializeUsers() {
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
    }

    /**
     * A helper method to create the view grid.
     */
    private void initializeGrid() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(difficulty);
        gridView.setBoardManager(matchingBoardManager);
        matchingBoardManager.getBoard().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / difficulty;
                        columnHeight = displayHeight / 4;
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
        for (int row = 0; row < 4; row++) {
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
    }

    /**
     * The listener for the undo button.
     */
    private void addUndoButtonListener() {
        final Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(view -> {
            matchingBoardManager.undoMove();
            if (matchingBoardManager.undoUsed()) {
                TextView undoText = findViewById(R.id.undoInfo);
                undoText.setText(R.string.none_remaining);
                undoButton.setEnabled(false);
                undoButton.setAlpha(.5f);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        updateScore();
        int moves = matchingBoardManager.getNumMoves() % 3;
        if (moves == 0 && !gameWon) {
            currentUser.getSaves().put(MatchingFragment.GAME_TITLE, matchingBoardManager);
            saveUserAccounts(userAccounts);
        }
        updateScore();
        if (matchingBoardManager.gameFinished()) {
            gameWon = true;
            createToast("You Win!");
            updateLeaderBoard("Matching Cards", new Score(currentUser.getName(), score));
        }
        display();
    }

    /**
     * Display the score as you play the game.
     */
    private void updateScore() {
        score = matchingBoardManager.generateScore();
        TextView curScore = findViewById(R.id.curScore);
        String temp = "Score: " + String.valueOf(score);
        curScore.setText(temp);
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateCardButtons();
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
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
            createToast("Your score is " + matchingBoardManager.generateScore());
        }
        finish();
    }


    /**
     * Display a toast message.
     *
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(
                this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Passes context of the activity to utility interface
     *
     * @return Context of current activity
     */
    public Context getActivity() {
        return this;
    }
}