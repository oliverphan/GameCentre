package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CancellationException;

import gamelauncher.LoginActivity;
import gamelauncher.SlidingTileActivity;
import users.User;

/**
 * The game activity.
 */
public class SlidingTileGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadGameFromFile();
        loadUserFromFile();
        difficulty = boardManager.getDifficulty();
        createTileButtons();
        setContentView(R.layout.activity_slidingtilesgame);
        addUserButtonListener();
        addUndoButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(difficulty);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
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
     * Create the buttons for displaying the tiles.
     */
    private void createTileButtons() {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row < difficulty; row++) {
            for (int col = 0; col < difficulty; col++) {
                Button tmp = new Button(getApplicationContext());
                if (!boardManager.userTiles) {
                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                } else {
                    tmp.setBackground(new BitmapDrawable(getResources(), board.getTile(row, col).getUserImage()));
                }
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / difficulty;
            int col = nextPos % difficulty;
            if (!boardManager.userTiles) {
                b.setBackgroundResource(board.getTile(row, col).getBackground());
            } else {
                if (!gameWon && board.getTile(row, col).getId() == difficulty*difficulty) {
                    b.setBackgroundResource(R.drawable.whitespace);
                } else {
                    b.setBackground(new BitmapDrawable(getResources(), board.getTile(row, col).getUserImage()));
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
        if (!boardManager.userTiles)
            userButton.setText(R.string.user_image_button_unpressed);
        else
            userButton.setText(R.string.user_image_button_pressed);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!boardManager.userTiles) {
                    pickImageFromGallery();
                } else {
                    userButton.setText(R.string.user_image_button_unpressed);
                    boardManager.userTiles = false;
                    display();
                }
            }
        });
    }

    /**
     * The listener for the undo button.
     */
    private void addUndoButtonListener() {
        final Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movesToUndo = findViewById(R.id.inputUndo);
                try {
                    int moves = Integer.valueOf(movesToUndo.getText().toString());
                    boardManager.undoMove(moves);
                } catch (NumberFormatException e) {
                    boardManager.undoMove(1);
                }
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
            Board board = boardManager.getBoard();
            boardManager.userTiles = true;
            for (int nextPos = 0; nextPos < board.numTiles(); nextPos++) {
                int row = nextPos / difficulty;
                int col = nextPos % difficulty;
                board.getTile(row, col).createUserTiles(userImage, difficulty);
                display();
            }
        }
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
    private void switchToSlidingTilesTitleActivity() {
        loadUserFromFile();
        loadUsersFromFile();
        saveAccountsToFile(LoginActivity.ACCOUNTS_SAVE_FILENAME);
        saveUserToFile(LoginActivity.USER_SAVE_FILENAME);
        if (!gameWon) {
            createToast("Saved");
        } else {
            createToast("Saved Wiped");
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        switchToSlidingTilesTitleActivity();
    }

    @Override
    public void update(Observable o, Object arg) {
        int moves = boardManager.getNumMoves() % 10;
        // Autosave - Old boardManager is replaced if there is one.
        if (moves == 0 && !gameWon) {
            currentUser.getSaves().put(SlidingTileActivity.GAME_TITLE, boardManager);
            saveAccountsToFile(LoginActivity.ACCOUNTS_SAVE_FILENAME);
        }
        if (boardManager.puzzleSolved()) {
            gameWon = true;
            createToast("You Win!");
        }
        display();
    }

    /**
     * Load the board manager from fileName.
     */
    private void loadGameFromFile() {
        try {
            InputStream inputStream = this.openFileInput(SlidingTileActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
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
     * Load the board manager from fileName.
     */
    @SuppressWarnings("unchecked")
    private void loadUsersFromFile() {
        try {
            InputStream inputStream = this.openFileInput(LoginActivity.ACCOUNTS_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userAccounts = (HashMap<String, User>) input.readObject();
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

    /**
     * Load the board manager from fileName.
     */
    @SuppressWarnings("unchecked")
    private void loadUserFromFile() {
        try {
            InputStream inputStream = this.openFileInput(LoginActivity.USER_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                currentUser = (User) input.readObject();
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

    /**
     * Store the new score and delete the old save in the User if the game is won.
     * If game hasn't been won, store the most recent boardManager to the User.
     */
    public void writeNewValues() {
        if (!gameWon) {
            currentUser.writeGame(SlidingTileActivity.GAME_TITLE, boardManager);
        } else {
            currentUser.setNewScore(SlidingTileActivity.GAME_TITLE, boardManager.generateScore());
            currentUser.deleteSave(SlidingTileActivity.GAME_TITLE);
        }
    }

    /**
     * Save the user account info to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveUserToFile(String fileName) {
        try {
            loadUsersFromFile();
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            writeNewValues();
            outputStream.writeObject(currentUser);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the user account info to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveAccountsToFile(String fileName) {
        try {
            loadUsersFromFile();
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            writeNewValues();
            userAccounts.put(currentUser.getName(), currentUser);
            outputStream.writeObject(userAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}