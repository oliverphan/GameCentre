package fall2018.csc2017.connectfour;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import fall2018.csc2017.R;
import fall2018.csc2017.common.CustomAdapter;
import fall2018.csc2017.common.GestureDetectGridView;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.SaveAndLoadGames;
import fall2018.csc2017.gamelauncher.FourFragment;
import fall2018.csc2017.scoring.LeaderBoard;
import fall2018.csc2017.scoring.Score;
import fall2018.csc2017.users.User;

public class FourGameActivity extends AppCompatActivity implements Observer,
        SaveAndLoadFiles, SaveAndLoadGames {

    /**
     * The SlidingBoard manager.
     */
    private FourBoardManager fourBoardManager;

    /**
     * The buttons on the board.
     */
    private List<Button> boardButtons;

    /**
     * The difficulty of the current game.
     */
    private int difficulty;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Current user logged in.
     */
    private User currentUser;

    /**
     * HashMap of all userAccounts
     */
    private Map<String, User> userAccounts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectfourgame);
        fourBoardManager = (FourBoardManager) loadGameFromFile(FourFragment.TEMP_SAVE_FILENAME);
        updateScore();
        initializeUsers();
        difficulty = fourBoardManager.getDifficulty();
        createBoardButtons();
        initializeGrid();
        if (fourBoardManager.getCurPlayer() == 2)
            fourBoardManager.makeAIMove();
    }

    /**
     * A helper method to initialize the user information.
     */
    public void initializeUsers() {
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
    }

    /**
     * A helper method to create the view grid.
     */
    public void initializeGrid() {
        gridView = findViewById(R.id.gridView);
        gridView.setNumColumns(7);
        gridView.setBoardManager(fourBoardManager);
        fourBoardManager.getBoard().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / 7;
                        columnHeight = displayHeight / 6;
                        display();
                    }
                });
    }

    /**
     * Get the initial backgrounds of the pieces on the grid.
     */
    public void createBoardButtons() {
        FourBoard board = fourBoardManager.getBoard();
        boardButtons = new ArrayList<>();
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                Button tmp = new Button(getApplicationContext());
                tmp.setBackgroundResource(board.getPiece(col, row).getBackground());
                this.boardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds of the pieces on the grid.
     */
    public void updateBoardButtons() {
        FourBoard board = fourBoardManager.getBoard();
        int nextPos = 0;
        for (Button b : boardButtons) {
            int col = nextPos % 7;
            int row = nextPos / 7;
            b.setBackgroundResource(board.getPiece(col, row).getBackground());
            nextPos++;
        }
    }

    /**
     * Update and display changes to the gameBoard.
     */
    public void display() {
        updateBoardButtons();
        gridView.setAdapter(new CustomAdapter(boardButtons, columnWidth, columnHeight));
    }


    @Override
    public void update(Observable o, Object arg) {
        updateScore();
        FourBoard board = fourBoardManager.getBoard();
        if (fourBoardManager.gameFinished()) {
            createToast(board.isWinner(1) ? "You Win!" : "You Lose");
            LeaderBoard leaderBoard = loadLeaderBoard();
            leaderBoard.updateScores("Connect Four", new Score(
                    currentUser.getName(), fourBoardManager.generateScore()));
            saveLeaderBoard(leaderBoard);
        }
        display();
    }

    /**
     * Display the score as you play the game.
     */
    private void updateScore() {
        int score = fourBoardManager.generateScore();
        TextView curScore = findViewById(R.id.curScore);
        String temp = "Score: " + score;
        curScore.setText(temp);
    }

    @Override
    public void onBackPressed() {
        switchToConnectFourActivity();
    }

    /**
     * Method to switch back to previous Activity
     */
    public void switchToConnectFourActivity() {
        writeNewValues(currentUser, FourFragment.GAME_TITLE, fourBoardManager);
        saveUserAccounts(userAccounts);
        if (!fourBoardManager.gameFinished()) {
            createToast("Saved");
        } else {
            createToast("Your score is " + fourBoardManager.generateScore());
        }
        finish();
    }

    /**
     * Display a message to the user in a Toast.
     *
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(
                this, msg, Toast.LENGTH_LONG).show();
    }

    public Context getActivity() {
        return this;
    }
}
