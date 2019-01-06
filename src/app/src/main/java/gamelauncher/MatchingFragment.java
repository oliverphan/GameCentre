package fall2018.csc2017.gamelauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.SaveAndLoadGames;
import fall2018.csc2017.matchingcards.MatchingBoardManager;
import fall2018.csc2017.matchingcards.MatchingGameActivity;
import fall2018.csc2017.scoring.LeaderBoardActivity;
import fall2018.csc2017.users.User;

public class MatchingFragment extends Fragment implements SaveAndLoadFiles, SaveAndLoadGames {

    /**
     * Tag for the current game being played.
     */
    public static final String GAME_TITLE = "Matching Cards";

    /**
     * Save file for the matchingBoardManager being created
     */
    public static final String TEMP_SAVE_FILENAME = "mc_save_file.ser";

    /**
     * The name of the current logged in user.
     */
    private User currentUser;

    /**
     * The board manager.
     */
    private MatchingBoardManager matchingBoardManager;

    /**
     * A HashMap of all the Users created. The key is the username, the value is the User object.
     */
    private Map<String, User> userAccounts;

    /**
     * String to signify Game Start
     */
    private String startMessage = "Game Start";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching, container, false);
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        addLaunchGame3Listener(view);
        addLaunchGame4Listener(view);
        addLaunchGame5Listener(view);
        addLoadButtonListener(view);
        addLeaderBoardListener(view);
        return view;
    }

    /**
     * Activate the start button for a 4 x 3 game.
     */
    private void addLaunchGame3Listener(View view) {
        Button startButton = view.findViewById(R.id.launchGame43);
        startButton.setOnClickListener(v -> {
            matchingBoardManager = new MatchingBoardManager(3);
            createToast(startMessage);
            switchToMatchingGameActivity();
        });
    }

    /**
     * Activate the start button for a 4 x 4 game.
     */
    private void addLaunchGame4Listener(View view) {
        Button startButton = view.findViewById(R.id.launchGame44);
        startButton.setOnClickListener(v -> {
            matchingBoardManager = new MatchingBoardManager(4);
            createToast(startMessage);
            switchToMatchingGameActivity();
        });
    }

    /**
     * Activate the start button for a 4 x 5 game.
     */
    private void addLaunchGame5Listener(View view) {
        Button startButton = view.findViewById(R.id.launchGame45);
        startButton.setOnClickListener(v -> {
            matchingBoardManager = new MatchingBoardManager(5);
            createToast(startMessage);
            switchToMatchingGameActivity();
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener(View view) {
        Button loadButton = view.findViewById(R.id.LoadButton);
        final boolean saveFileExists = currentUser.getSaves().containsKey(GAME_TITLE);
        loadButton.setOnClickListener(v -> {
            createToast("Game Loaded");
            matchingBoardManager =
                    (MatchingBoardManager) currentUser.getSaves().get(GAME_TITLE);
            switchToMatchingGameActivity();
        });
        loadButton.setAlpha(saveFileExists ? 1.0f : 0.5f);
        loadButton.setClickable(saveFileExists);
    }

    /**
     * Activate the LeaderBoard button
     *
     * @param view the current fragment being displayed
     */
    private void addLeaderBoardListener(View view) {
        Button leaderBoardButton = view.findViewById(R.id.LeaderBoardButton);
        leaderBoardButton.setOnClickListener(v -> switchToLeaderBoardActivity());
    }

    public void switchToLeaderBoardActivity() {
        Intent tmp = new Intent(getActivity(), LeaderBoardActivity.class);
        tmp.putExtra("frgToLoad", 2);
        startActivity(tmp);
        FragmentActivity o = getActivity();
        if (o != null) {
            o.finish();
        }
    }

    /**
     * Switch to the MatchingCardsGameActivity view
     */
    private void switchToMatchingGameActivity() {
        Intent tmp = new Intent(getActivity(), MatchingGameActivity.class);
        saveGameToFile(TEMP_SAVE_FILENAME, matchingBoardManager);
        startActivity(tmp);
    }

    @Override
    public void onResume() {
        super.onResume();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        if (getView() != null) {
            addLoadButtonListener(getView());
        }
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}