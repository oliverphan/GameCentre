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

import gamelauncher.IntentKeys;
import gamelauncher.LoginActivity;
import gamelauncher.SlidingTileTitleActivity;
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
     * Boolean for custom image tiles.
     */
    private boolean userTiles;

    /**
     * Request Code for gallery activity
     */
    public static final int IMAGEREQUESTCODE = 100;

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
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        difficulty = intent.getIntExtra(IntentKeys.DIFFICULTY_KEY, 4);
        currentUser = (User) intent.getSerializableExtra(IntentKeys.CURRENTUSER_KEY);
        userAccounts = (HashMap<String, User>) intent.getSerializableExtra(IntentKeys.USERACCOUNTS_KEY);
        boolean toLoad = intent.getBooleanExtra(IntentKeys.TOLOAD_KEY, false);
        if (toLoad) {
            loadFromFile(LoginActivity.SAVE_FILENAME);
            userTiles = boardManager.userTiles;
        } else {
            boardManager = new BoardManager(difficulty);
            userTiles = boardManager.userTiles;
            saveToFile(LoginActivity.SAVE_FILENAME);
        }
        createTileButtons();
        setContentView(R.layout.activity_slidingtiles);
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
        for (int row = 0; row != difficulty; row++) {
            for (int col = 0; col != difficulty; col++) {
                Button tmp = new Button(getApplicationContext());
                if (!userTiles) {
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
            if (!userTiles) {
                b.setBackgroundResource(board.getTile(row, col).getBackground());
            } else {
                b.setBackground(new BitmapDrawable(getResources(), board.getTile(row, col).getUserImage()));
            }
            nextPos++;
        }
    }

    /**
     * The listener for the add user images button.
     */
    private void addUserButtonListener() {
        Button userButton = findViewById(R.id.user);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userTiles) {
                    pickImageFromGallery();
                } else {
                    userTiles = false;
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
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE);
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
                case IMAGEREQUESTCODE:
                    createBitmapFromUri(i.getData());
                    break;
            }
        }
        if (userImage != null) {
            Board board = boardManager.getBoard();
            userTiles = true;
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
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    @SuppressWarnings({"unchecked", "SameParameterValue"})
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userAccounts = (HashMap<String, User>) input.readObject();
                currentUser = userAccounts.get(currentUser.getName());
                boardManager = (BoardManager) currentUser.getSaves().get(SlidingTileTitleActivity.GAME_TITLE);
                difficulty = boardManager.getDifficulty();
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
     * Save the user account info to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            if (!gameWon) {
                userAccounts.get(currentUser.getName()).getSaves().put(SlidingTileTitleActivity.GAME_TITLE, boardManager);
            }else{
                userAccounts.get(currentUser.getName()).getSaves().remove(SlidingTileTitleActivity.GAME_TITLE);
            }
            outputStream.writeObject(userAccounts);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Switch to the title screen. Only to be called when the game is won.
     */
    private void switchToSlidingTilesTitleActivity() {
        Intent tmp = new Intent(this, SlidingTileTitleActivity.class);
        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        tmp.putExtra(IntentKeys.USERACCOUNTS_KEY, userAccounts);
        saveToFile(LoginActivity.SAVE_FILENAME);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        if (gameWon) {
            userAccounts.get(currentUser.getName()).setNewScore(SlidingTileTitleActivity.GAME_TITLE, boardManager.generateScore());
        }
        switchToSlidingTilesTitleActivity();

    }

    @Override
    public void update(Observable o, Object arg) {
        int moves = boardManager.getNumMoves() % 10;
        if (moves == 0 && !gameWon) {
            saveToFile(LoginActivity.SAVE_FILENAME);
        }
        if (boardManager.puzzleSolved()) {
            gameWon = true;
            Toast.makeText(this, "You Win!", Toast.LENGTH_LONG).show();
        }
        display();
    }
}
