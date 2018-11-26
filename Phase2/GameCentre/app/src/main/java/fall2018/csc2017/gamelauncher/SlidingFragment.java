package fall2018.csc2017.gamelauncher;

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


import java.io.IOException;

import java.io.ObjectOutputStream;
import java.util.HashMap;

import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.slidingtiles.SlidingBoardManager;
import fall2018.csc2017.R;
import fall2018.csc2017.slidingtiles.SlidingGameActivity;
import fall2018.csc2017.scoring.LeaderBoardActivity;
import fall2018.csc2017.users.User;

public class SlidingFragment extends Fragment implements SaveAndLoadFiles {
    /**
     * The name of the game.
     */
    public static final String GAME_TITLE = "Sliding Tiles";

    /**
     * The main save file.
     */
    public static final String TEMP_SAVE_FILENAME = "tmp_save_file.ser";

    /**
     * The name of the current logged in user.
     */
    private User currentUser;

    /**
     * The board manager.
     */
    private SlidingBoardManager slidingBoardManager;

    /**
     * A HashMap of all the Users created. The key is the username, the value is the User object.
     */
    private HashMap<String, User> userAccounts;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_slidingtiles, container, false);
        Bundle args = getArguments();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
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
                slidingBoardManager = new SlidingBoardManager(3);
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
                slidingBoardManager = new SlidingBoardManager(4);
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
                slidingBoardManager = new SlidingBoardManager(5);
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
                    slidingBoardManager = (SlidingBoardManager) currentUser.getSaves().get(GAME_TITLE);
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
        Intent tmp = new Intent(getActivity(), SlidingGameActivity.class);
        saveGameToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the Leaderboard view
     */
    private void switchToLeaderBoardActivity() {
        Intent tmp = new Intent(getActivity(), LeaderBoardActivity.class);
        tmp.putExtra("frgToLoad", 0);
        startActivity(tmp);
    }

    @Override
    // Probably not needed
    public void onResume() {
        super.onResume();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        addLoadButtonListener(getView());
    }

    /**
     * Save the slidingBoardManager for passing game around.
     *
     * @param fileName the name of the file
     */
    public void saveGameToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(fileName, getContext().MODE_PRIVATE));
            outputStream.writeObject(slidingBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}