package fall2018.csc2017.scoring;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;

public class SlidingTileLeaderBoardFragment extends Fragment implements SaveAndLoadFiles {
    /**
     * The leaderBoard.
     */
    private LeaderBoard leaderBoard;

    //    Basically onCreate
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_slidingtile_leaderboard, container, false);
        Bundle args = getArguments();
        leaderBoard = loadLeaderBoard();
        displayLeaders(view,"Sliding Tiles");
        return view;
    }

    /**
     * Write the score values and username to the TextView display.
     *
     * @param gameName The name of the game to display top scores
     */
    private void displayLeaders(View view, String gameName) {
        ArrayList<Score> tempScores = leaderBoard.getTopScores(gameName);
        TextView firstUser = view.findViewById(R.id.user1);
        TextView firstScore = view.findViewById(R.id.score1);
        String temp1 = tempScores.get(0).getUsername();
        String temp2 = String.valueOf(tempScores.get(0).getValue());
        firstUser.setText(temp1);
        firstScore.setText(temp2);
        TextView secondUser = view.findViewById(R.id.user2);
        TextView secondScore = view.findViewById(R.id.score2);
        String temp3 = tempScores.get(1).getUsername();
        String temp4 = String.valueOf(tempScores.get(1).getValue());
        secondUser.setText(temp3);
        secondScore.setText(temp4);
        TextView thirdUser = view.findViewById(R.id.user3);
        TextView thirdScore = view.findViewById(R.id.score3);
        String temp5 = tempScores.get(2).getUsername();
        String temp6 = String.valueOf(tempScores.get(2).getValue());
        thirdUser.setText(temp5);
        thirdScore.setText(temp6);
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
