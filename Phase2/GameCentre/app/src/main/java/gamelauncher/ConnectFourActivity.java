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

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.connectfour.ConnectFourGameActivity;

public class ConnectFourActivity extends Fragment {
    public static final String TEMP_SAVE_FILENAME = "tmp_save_file.ser";
//    private ConnectFourBoardManager boardManager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_connectfour, container, false);
        Bundle args = getArguments();
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
//                boardManager = new ConnectFourBoardManager(1);
                switchToConnectFourGameActivity();
            }
        });
    }

    private void addLaunchMediumListener(View view) {
        Button launchMediumButton = view.findViewById(R.id.LaunchMedium);
        launchMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Launch Medium Game", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLaunchHardListener(View view) {
        Button launchHardButton = view.findViewById(R.id.LaunchHard);
        launchHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Launch Hard Game", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLoadButtonListener(View view) {
        Button loadButton = view.findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loaded Game", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLeaderBoardListener(View view) {
        Button scoreBoardButton = view.findViewById(R.id.LeaderBoardButton);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Launched LeaderBoard", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void switchToConnectFourGameActivity() {
        Intent tmp = new Intent(getActivity(), ConnectFourGameActivity.class);
//        saveGameToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

//    public void saveGameToFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    getActivity().openFileOutput(fileName, getContext().MODE_PRIVATE));
//            outputStream.writeObject(boardManager);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
}

