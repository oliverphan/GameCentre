package gamelauncher;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.connectFour.FourBoardManager;
import fall2018.csc2017.connectFour.FourGameActivity;
import fall2018.csc2017.slidingtiles.R;
import users.User;

public class ConnectFourActivity extends Fragment {
    public static final String GAME_TITLE = "ConnectFour";
    private FourBoardManager boardManager;

    public static final String TEMP_SAVE_FILENAME = "c4_save_file.ser";

    private User currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_connectfour, container, false);
        Bundle args = getArguments();
        loadUserFromFile();
        addLaunchEasyListener(view);
        addLaunchMediumListener(view);
        addLaunchHardListener(view);
        addLoadButtonListener(view);
        addLeaderBoardListener(view);
        return view;
    }

    private void addLaunchEasyListener(View view) {
        Button launchEasyButton = view.findViewById(R.id.LaunchEasy);
        launchEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(getActivity(), FourGameActivity.class);
                boardManager = new FourBoardManager(0);
                createToast("Game Start");
                switchToFourGameActivity();
            }
        });
    }

    private void addLaunchMediumListener(View view) {
        Button launchMediumButton = view.findViewById(R.id.LaunchMedium);
        launchMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new FourBoardManager(1);
                createToast("Game Start");
                switchToFourGameActivity();
            }
        });
    }

    private void addLaunchHardListener(View view) {
        Button launchHardButton = view.findViewById(R.id.LaunchHard);
        launchHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new FourBoardManager(2);
                createToast("Game Start");
                switchToFourGameActivity();
            }
        });
    }

    private void addLoadButtonListener(View view) {
        Button loadButton = view.findViewById(R.id.LoadButton);
        final boolean saveFileExists = currentUser.getSaves().containsKey(GAME_TITLE);
        loadButton.setAlpha(1);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveFileExists) {
                    createToast("Game Loaded");
                    boardManager = (FourBoardManager) currentUser.getSaves().get(GAME_TITLE);
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

    private void addLeaderBoardListener(View view) {
        Button scoreBoardButton = view.findViewById(R.id.LeaderBoardButton);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createToast("Launched LeaderBoard");
            }
        });

    }

    /**
     * Switch to the SlidingTilesGameActivity view
     */
    private void switchToFourGameActivity() {
        Intent tmp = new Intent(getActivity(), FourGameActivity.class);
        saveGameToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
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

    @Override
    // Probably not needed
    public void onResume() {
        super.onResume();
        loadUserFromFile();
        addLoadButtonListener(getView());
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
