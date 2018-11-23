package gamelauncher;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.R;

public class MemoryActivity extends Fragment {

//    Basically onCreate
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matching, container, false);
        Bundle args = getArguments();

        addStartGameListener(view);
        addLoadGameListener(view);
        addLeaderBoardListener(view);
        return view;
    }

    /**
     * Activate the start button.
     */
    private void addStartGameListener(View view) {
        Button startButton = view.findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DO SOMETHING
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadGameListener(View view) {
        Button loadButton = view.findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DO SOMETHING
            }
        });
    }

    /**
     * Activate the button to go the leaderboard.
     */
    private void addLeaderBoardListener(View view) {
        Button leaderBoardButton = view.findViewById(R.id.LeaderBoardButton);
        leaderBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DO SOMETHING
            }
        });
    }

    /**
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}