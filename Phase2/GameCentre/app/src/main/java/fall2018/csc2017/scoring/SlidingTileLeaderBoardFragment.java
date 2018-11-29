package fall2018.csc2017.scoring;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import fall2018.csc2017.R;
import fall2018.csc2017.common.SaveAndLoadFiles;
import fall2018.csc2017.common.ScoreDisplay;
import fall2018.csc2017.users.User;

public class SlidingTileLeaderBoardFragment extends Fragment implements SaveAndLoadFiles,
        ScoreDisplay {
    /**
     * The leaderBoard.
     */
    private LeaderBoard leaderBoard;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_slidingtile_leaderboard, container,
                false);
        leaderBoard = loadLeaderBoard();
        User currentUser = loadUserAccounts().get(loadCurrentUsername());
        TextView userScore = view.findViewById(R.id.user_high_score);
        userScore.setText(String.valueOf(currentUser.getScores().get("Sliding Tiles")));
        displayLeaders(view, leaderBoard,"Sliding Tiles");
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
