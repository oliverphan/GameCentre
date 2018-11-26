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
import fall2018.csc2017.common.SaveAndLoad;
import fall2018.csc2017.common.ScoreDisplay;

public class FourLeaderBoardFragment extends Fragment implements SaveAndLoad, ScoreDisplay {
    /**
     * The leaderBoard.
     */
    private LeaderBoard leaderBoard;

    //    Basically onCreate
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_connectfour_leaderboard, container, false);
        Bundle args = getArguments();
        leaderBoard = loadLeaderBoard();
        displayLeaders(view, leaderBoard, "Connect Four");
        return view;
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
