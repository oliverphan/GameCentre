package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.common.SaveAndLoad;
import fall2018.csc2017.common.CustomAdapter;
import fall2018.csc2017.common.GestureDetectGridView;
import gamelauncher.SlidingFragment;
import scoring.LeaderBoard;
import scoring.Score;
import users.User;
import fall2018.csc2017.R;

/**
 * The game activity.
 */
public class SlidingGameActivity extends AppCompatActivity implements Observer, SaveAndLoad {

    /**
     * The board manager.
     */
    private SlidingBoardManager slidingBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The HashMap of all the users by name.
     */
    private HashMap<String, User> userAccounts;

    /**
     * The name of the current user.
     */
    private User currentUser;

    /**
     * The number of tiles per side of the board.
     */
    private int difficulty;

    /**
     * Request Code for gallery activity
     */
    public static final int IMAGE_REQUEST_CODE = 100;

    /**
     * EditText of the number of moves to undo.
     */
    private EditText movesToUndo;

    /**
     * Image uploaded by user
     */
    private Bitmap userImage;

    /**
     * A boolean tracking whether the game has been won.
     */
    private boolean gameWon;


    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    public Context getActivity() {
        return this;
    }


    /**
     * Create the buttons for displaying the tiles.
     */
    private void createTileButtons() {
        SlidingBoard slidingBoard = slidingBoardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row < difficulty; row++) {
            for (int col = 0; col < difficulty; col++) {
                Button tmp = new Button(getApplicationContext());
                if (!slidingBoardManager.userTiles) {
                    tmp.setBackgroundResource(slidingBoard.getTile(row, col).getBackground());
                } else {
                    tmp.setBackground(new BitmapDrawable(getResources(), slidingBoard.getTile(row, col).getUserImage()));
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SlidingBoard slidingBoard = slidingBoardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / difficulty;
            int col = nextPos % difficulty;
            if (!slidingBoardManager.userTiles) {
                b.setBackgroundResource(slidingBoard.getTile(row, col).getBackground());
            } else {
                if (!gameWon && slidingBoard.getTile(row, col).getId() == difficulty * difficulty) {
                    b.setBackgroundResource(R.drawable.whitespace);
                } else {
                    b.setBackground(new BitmapDrawable(getResources(), slidingBoard.getTile(row, col).getUserImage()));
                }
            }
            nextPos++;
        }
    }

    /**
     * The listener for the add user images button.
     */
    private void addUserButtonListener() {
        final Button userButton = findViewById(R.id.user);
        // Try to get a ternary working here
        if (!slidingBoardManager.userTiles)
            userButton.setText(R.string.user_image_button_unpressed);
        else
            userButton.setText(R.string.user_image_button_pressed);
        userButton.setOnClickListener(view -> {
            if (!slidingBoardManager.userTiles) {
                pickImageFromGallery();
            } else {
                userButton.setText(R.string.user_image_button_unpressed);
                slidingBoardManager.userTiles = false;
                display();
            }
        });
    }

    /**
     * The listener for the undo button.
     */
    private void addUndoButtonListener() {
        final Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(view -> {
            movesToUndo = findViewById(R.id.inputUndo);
            try {
                int moves = Integer.valueOf(movesToUndo.getText().toString());
                slidingBoardManager.undoMove(moves);
            } catch (NumberFormatException e) {
                slidingBoardManager.undoMove(1);
            }
        });
    }

    /**
     * Start activity to pick image from user gallery.
     */
    private void pickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadGameFromFile();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        difficulty = slidingBoardManager.getDifficulty();
        createTileButtons();
        setContentView(R.layout.activity_slidingtilesgame);
        addUserButtonListener();
        addUndoButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(difficulty);
        gridView.setBoardManager(slidingBoardManager);
        slidingBoardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
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
     * Converts uri from gallery to bitmap
     *
     * @param imageUri uri of selected image.
     */
    private void createBitmapFromUri(Uri imageUri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (Exception e) {
            Log.e("GalleryAccessActivity", "Image select error", e);
        }
        if (bitmap != null) {
            userImage = Bitmap.createScaledBitmap(bitmap, 247 * difficulty, 391 * difficulty, true);
        }
    }

    /**
     * Switch to the title screen. Only to be called when the game is won.
     */
    private void switchToSlidingTilesActivity() {
        writeNewValues();
        saveUserAccounts(userAccounts);
        if (!gameWon) {
            createToast("Saved");
        } else {
            createToast("Saved Wiped");
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        switchToSlidingTilesActivity();
    }

    /**
     * Load the board manager from fileName.
     */
    private void loadGameFromFile() {
        try {
            InputStream inputStream = this.openFileInput(SlidingFragment.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                slidingBoardManager = (SlidingBoardManager) input.readObject();
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
     * Store the new score and delete the old save in the User if the game is won.
     * If game hasn't been won, store the most recent slidingBoardManager to the User.
     */
    public void writeNewValues() {
        if (!gameWon) {
            currentUser.writeGame(SlidingFragment.GAME_TITLE, slidingBoardManager);
        } else {
            currentUser.setNewScore(SlidingFragment.GAME_TITLE, slidingBoardManager.generateScore());
            currentUser.deleteSave(SlidingFragment.GAME_TITLE);
        }
    }


    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * continuation of gallery pick activity
     *
     * @param requestCode Request code for activity
     * @param resultCode  Resulting code from Android
     * @param i           intent of activity
     */
    @Override
    protected final void onActivityResult(final int requestCode,
                                          final int resultCode, final Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    createBitmapFromUri(i.getData());
                    break;
            }
        }
        if (userImage != null) {
            final Button userButton = findViewById(R.id.user);
            userButton.setText(R.string.user_image_button_pressed);
            SlidingBoard slidingBoard = slidingBoardManager.getBoard();
            slidingBoardManager.userTiles = true;
            for (int nextPos = 0; nextPos < slidingBoard.numTiles(); nextPos++) {
                int row = nextPos / difficulty;
                int col = nextPos % difficulty;
                slidingBoard.getTile(row, col).createUserTiles(userImage, difficulty);
                display();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        int moves = slidingBoardManager.getNumMoves() % 10;
        // Autosave - Old boardManager is replaced if there is one.
        if (moves == 0 && !gameWon) {
            currentUser.getSaves().put(SlidingFragment.GAME_TITLE, slidingBoardManager);
            saveUserAccounts(userAccounts);
        }
        if (slidingBoardManager.gameFinished()) {
            gameWon = true;
            createToast("You Win!");
            LeaderBoard leaderBoard = loadLeaderBoard();
            leaderBoard.updateScores("Sliding Tiles", new Score(currentUser.getName(), slidingBoardManager.generateScore()));
            saveLeaderBoard(leaderBoard);
        }
        display();
    }
}