package fall2018.csc2017.scoring;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.ScoreDisplay;
import fall2018.csc2017.users.User;

public class FourLeaderBoardFragment extends Fragment implements SaveAndLoadFiles, ScoreDisplay {
    /**
     * The leaderBoard.
     */
    private LeaderBoard leaderBoard;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_connectfour_leaderboard, container,
                false);
        leaderBoard = loadLeaderBoard();
        User currentUser = loadUserAccounts().get(loadCurrentUsername());
        TextView userScore = view.findViewById(R.id.user_high_score);
        userScore.setText(String.valueOf(currentUser.getScores().get("Connect Four")));
        displayLeaders(view, leaderBoard, "Connect Four");
        return view;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveLeaderBoard(leaderBoard);
    }


}
