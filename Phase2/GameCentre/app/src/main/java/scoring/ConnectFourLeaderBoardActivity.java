package scoring;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fall2018.csc2017.R;
import fall2018.csc2017.SaveAndLoad;

public class ConnectFourLeaderBoardActivity extends Fragment implements SaveAndLoad {
    /**
     * The leaderBoard.
     */
    private LeaderBoard leaderBoard;

    //    Basically onCreate
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_connectfour_leaderboard, container, false);
        Bundle args = getArguments();
        leaderBoard = loadLeaderBoard();
        displayLeaders(view,"Connect Four");
        return view;
    }

    /**
     * Write the score values and username to the TextView display.
     *
     * @param gameName The name of the game to display top scores
     */
    private void displayLeaders(View view, String gameName) {
        TextView tvScores = view.findViewById(R.id.tv_scores);
        ArrayList<Score> tempScores = leaderBoard.getTopScores(gameName);
        ArrayList<Integer> tempScoreValues = new ArrayList<>();
        ArrayList<String> tempScoreUsers = new ArrayList<>();
        int numScores = tempScoreValues.size(); //numScores == the number of users too
        for (Score score: tempScores) {
            tempScoreValues.add(score.getValue());
            tempScoreUsers.add(score.getUsername());
        }
        StringBuilder sb = new StringBuilder(); // Build the display string
        for (int i = 1; i <= numScores; i++){
            if (tempScoreValues.get(i - 1) == -1){ // -1 means that slot hasn't been filled yet
                break;
            }
            sb.append(i);
            sb.append(": ");
            sb.append(tempScoreValues.get(i - 1));
            sb.append(" by ");
            sb.append(tempScoreUsers.get(i - 1));
            sb.append("\n");
        }
        tvScores.setText(sb.toString());
    }


    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onResume() {
        super.onResume();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    public void onPause() {
        super.onPause();
        saveLeaderBoard(leaderBoard);
    }


}
