package fall2018.csc2017.gamelauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import fall2018.csc2017.common.SaveAndLoadGames;
import fall2018.csc2017.connectfour.FourBoardManager;
import fall2018.csc2017.connectfour.FourGameActivity;
import fall2018.csc2017.R;
import fall2018.csc2017.scoring.LeaderBoardActivity;
import fall2018.csc2017.users.User;

public class FourFragment extends Fragment implements SaveAndLoadFiles, SaveAndLoadGames {
    /**
     * Tag for the current game being played.
     */
    public static final String GAME_TITLE = "Connect Four";
    /**
     * The SlidingBoard manager for the current game
     */
    private FourBoardManager fourBoardManager;

    /**
     * Save file for the boardManager being created
     */
    public static final String TEMP_SAVE_FILENAME = "c4_save_file.ser";

    /**
     * The current user logged in
     */
    private User currentUser;

    /**
     * A HashMap of all the Users created. The key is the username, the value is the User object.
     */
    private HashMap<String, User> userAccounts;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_connectfour, container, false);
        Bundle args = getArguments();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        addLaunchEasyListener(view);
        addLaunchMediumListener(view);
        addLaunchHardListener(view);
        addLoadButtonListener(view);
        addLeaderBoardListener(view);
        return view;
    }

    /**
     * Activate the Launch Easy Game button
     *
     * @param view the current fragment being displayed
     */
    private void addLaunchEasyListener(View view) {
        Button launchEasyButton = view.findViewById(R.id.LaunchEasy);
        launchEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourBoardManager = new FourBoardManager(0);
                createToast("Game Start");
                switchToFourGameActivity();
            }
        });
    }

    /**
     * Activate the Launch Medium Game button
     *
     * @param view the current fragment being displayed
     */
    private void addLaunchMediumListener(View view) {
        Button launchMediumButton = view.findViewById(R.id.LaunchMedium);
        launchMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourBoardManager = new FourBoardManager(1);
                createToast("Game Start");
                switchToFourGameActivity();
            }
        });
    }

    /**
     * Activate the Launch Hard Game button
     *
     * @param view the current fragment being displayed
     */
    private void addLaunchHardListener(View view) {
        Button launchHardButton = view.findViewById(R.id.LaunchHard);
        launchHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourBoardManager = new FourBoardManager(2);
                createToast("Game Start");
                switchToFourGameActivity();
            }
        });
    }

    /**
     * Activate the Load button
     *
     * @param view the current fragment being displayed
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
                    fourBoardManager = (FourBoardManager) currentUser.getSaves().get(GAME_TITLE);
                    switchToFourGameActivity();
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
     * Activate the LeaderBoard button
     *
     * @param view the current fragment being displayed
     */
    private void addLeaderBoardListener(View view) {
        Button scoreBoardButton = view.findViewById(R.id.LeaderBoardButton);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(getActivity(), LeaderBoardActivity.class);
                tmp.putExtra("frgToLoad", 1);
                startActivity(tmp);
            }
        });

    }

    /**
     * Switch to the ConnectFourGameActivity view
     */
    private void switchToFourGameActivity() {
        Intent tmp = new Intent(getActivity(), FourGameActivity.class);
        saveGameToFile(TEMP_SAVE_FILENAME, fourBoardManager);
        startActivity(tmp);
    }


    @Override
    public void onResume() {
        super.onResume();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        addLoadButtonListener(getView());
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
