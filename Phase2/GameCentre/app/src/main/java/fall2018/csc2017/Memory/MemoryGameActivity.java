//package fall2018.csc2017.Memory;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.ViewTreeObserver;
//import android.widget.Button;
//import android.widget.Toast;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.ObjectInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Observable;
//import java.util.Observer;
//
//import fall2018.csc2017.R;
//import fall2018.csc2017.common.SaveAndLoad;
//import fall2018.csc2017.common.GestureDetectGridView;
//import fall2018.csc2017.common.CustomAdapter;
//import gamelauncher.LoginActivity;
//import gamelauncher.MemoryFragment;
//import users.User;
//
///**
// * The game activity.
// */
//public class MemoryGameActivity extends AppCompatActivity implements Observer, SaveAndLoad {
//
//    /**
//     * The MemoryBoardManager.
//     */
//    private MemoryBoardManager memoryBoardManager;
//
//    /**
//     * The buttons to display.
//     */
//    private ArrayList<Button> cardButtons;
//
//    /**
//     * The Map of all the Users by name.
//     */
//    private HashMap<String, User> userAccounts;
//
//    /**
//     * The current User.
//     */
//    private User currentUser;
//
//    /**
//     * Number of cards per side of the board.
//     */
//    private int difficulty;
//
//    /**
//     * Boolean tracking whether the game has been won.
//     */
//    private boolean gameWon;
//
//
//    private GestureDetectGridView gridView;
//    private static int columnWidth, columnHeight;
//
//
//    /**
//     * Set up the background image for each button based on the master list
//     * of positions, and then call the adapter to set the view.
//     */
//    // Display
//    public void display() {
//        updateCardButtons();
//        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
//    }
//
//    public Context getActivity() {
//        return this;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_matching);
//        loadGameFromFile();
//        userAccounts = loadUserAccounts();
//        addUndoButtonListener();
//        difficulty = memoryBoardManager.getDifficulty();
//        createCardButtons();
//        // Add View to activity
//        gridView = findViewById(R.id.grid);
//        gridView.setNumColumns(difficulty);
//        gridView.setMemoryBoardManager(memoryBoardManager);
//        memoryBoardManager.getMemoryBoard().addObserver(this);
//        // Observer sets up desired dimensions as well as calls to our display function
//        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
//                                this);
//                        int displayWidth = gridView.getMeasuredWidth();
//                        int displayHeight = gridView.getMeasuredHeight();
//
//                        columnWidth = displayWidth / difficulty;
//                        columnHeight = displayHeight / difficulty;
//                        display();
//                    }
//                });
//    }
//
//    /**
//     * Create the buttons for displaying the cards.
//     */
//    private void createCardButtons() {
//        MemoryBoard board = memoryBoardManager.getMemoryBoard();
//        cardButtons = new ArrayList<>();
//        for (int row = 0; row < difficulty; row++) {
//            for (int col = 0; col < difficulty; col++) {
//                Button tmp = new Button(getApplicationContext());
//                tmp.setBackgroundResource(board.getCard(row, col).getBackground());
//                this.cardButtons.add(tmp);
//            }
//        }
//    }
//
//    /**
//     * Update the backgrounds on the buttons to match the cards.
//     */
//    private void updateCardButtons() {
//        MemoryBoard memoryBoard = memoryBoardManager.getMemoryBoard();
//        // Updated pictures in event of a tap
//        // Whether to tap to the front or the back
//    }
//
//    /**
//     * The listener for the undo button.
//     */
//    private void addUndoButtonListener() {
//        final Button undoButton = findViewById(R.id.undoButton);
//        undoButton.setOnClickListener(view ->
//                memoryBoardManager.undoMove());
//    }
//
//    /**
//     * Switch to the title screen. Only to be called when the game is won.
//     */
//    private void switchToMemoryTitleActivity() {
//
//        writeNewValues();
//        saveUserAccounts(userAccounts);
//        if (!memoryBoardManager.gameFinished()) {
//            createToast("Saved");
//        } else {
//            createToast("Saved Wiped");
//        }
//        finish();
//    }
//
//    /**
//     * Load the board manager from fileName.
//     */
//    private void loadGameFromFile() {
//        try {
//            InputStream inputStream = this.openFileInput(MemoryFragment.TEMP_SAVE_FILENAME);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                memoryBoardManager = (MemoryBoardManager) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        }
//    }
//
//    /**
//     * Store the new score and delete the old save in the User if the game is won.
//     * If game hasn't been won, store the most recent boardManager to the User.
//     */
//    public void writeNewValues() {
//        if (!memoryBoardManager.gameFinished()) {
//            currentUser.writeGame(MemoryFragment.GAME_TITLE, memoryBoardManager);
//        } else {
//            currentUser.setNewScore(MemoryFragment.GAME_TITLE, memoryBoardManager.generateScore());
//            currentUser.deleteSave(MemoryFragment.GAME_TITLE);
//        }
//    }
//
//    /**
//     * @param msg The message to be displayed in the Toast.
//     */
//    private void createToast(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onBackPressed() {
//        switchToMemoryTitleActivity();
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        //TODO
//    }
//}