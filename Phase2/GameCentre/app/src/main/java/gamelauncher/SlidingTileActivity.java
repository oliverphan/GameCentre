package gamelauncher;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.slidingtiles.BoardManager;
import fall2018.csc2017.R;
import fall2018.csc2017.slidingtiles.SlidingTileGameActivity;
import scoring.LeaderBoardActivity;
import users.User;

public class SlidingTileActivity extends Fragment {
    /**
     * The name of the game.
     */
    public static final String GAME_TITLE = "Sliding Tiles";

    /**
     * The main save file.
     */
    public static final String TEMP_SAVE_FILENAME = "tmp_save_file.ser";

    /**
     * The current logged in user.
     */
    private User currentUser;

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_slidingtiles, container, false);
        Bundle args = getArguments();
        loadUserFromFile();
        addLeaderBoardListener(view);
        addLaunchGame3Listener(view);
        addLaunchGame4Listener(view);
        addLaunchGame5Listener(view);
        addLoadButtonListener(view);
        return view;
    }

    /**
     * Activate the Sliding Tiles 3x3 button.
     */
    private void addLaunchGame3Listener(View view) {
        Button launchGame3Button = view.findViewById(R.id.LaunchGame3);
        launchGame3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(3);
                createToast("Game Start");
                switchToSlidingTileGameActivity();
            }
        });
    }

    /**
     * Activate the Sliding Tiles 4x4 button.
     */
    private void addLaunchGame4Listener(View view) {
        Button launchGame4Button = view.findViewById(R.id.LaunchGame4);
        launchGame4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(4);
                createToast("Game Start");
                switchToSlidingTileGameActivity();
            }
        });
    }

    /**
     * Activate the Sliding Tiles 5x5 button.
     */
    private void addLaunchGame5Listener(View view) {
        Button launchGame5Button = view.findViewById(R.id.LaunchGame5);
        launchGame5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5);
                createToast("Game Start");
                switchToSlidingTileGameActivity();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener(View view) {
        Button loadButton = view.findViewById(R.id.LoadButton);
        final boolean saveFileExists = currentUser.getSaves().containsKey(GAME_TITLE);
        loadButton.setAlpha(1);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveFileExists) {
                    createToast("Game Loaded");
                    boardManager = (BoardManager) currentUser.getSaves().get(GAME_TITLE);
                    switchToSlidingTileGameActivity();
                } else {
                    createToast("No File Exists!");
                }
            }
        });
        if (!saveFileExists) {
            loadButton.setClickable(saveFileExists);
            loadButton.setAlpha(.5f);
        }
    }

    /**
     * Activate the scoreboard of top scores button.
     */
    private void addLeaderBoardListener(View view) {
        Button scoreBoardButton = view.findViewById(R.id.LeaderBoardButton);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createToast("Launched LeaderBoard");
                switchToLeaderBoardActivity();
            }
        });
    }

    /**
     * Switch to the SlidingTilesGameActivity view
     */
    private void switchToSlidingTileGameActivity() {
        Intent tmp = new Intent(getActivity(), SlidingTileGameActivity.class);
        saveGameToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreBoard view
     */
    private void switchToLeaderBoardActivity() {
        Intent tmp = new Intent(getActivity(), LeaderBoardActivity.class);
//        tmp.putExtra(IntentKeys.GAME_TITLE_KEY, GAME_TITLE);
//        tmp.putExtra(IntentKeys.CURRENTUSER_KEY, currentUser);
        startActivity(tmp);
    }

    @Override
    // Probably not needed
    public void onResume() {
        super.onResume();
        loadUserFromFile();
        addLoadButtonListener(getView());
    }

    /**
     * Save the boardManager for passing game around.
     *
     * @param fileName the name of the file
     */
    public void saveGameToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(fileName, getContext().MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUserFromFile() {
        try {
            InputStream inputStream = getActivity().openFileInput(LoginActivity.USER_SAVE_FILENAME);
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
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}