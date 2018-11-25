package scoring;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fall2018.csc2017.R;

public class SlidingTileLeaderBoardActivity extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_tile_leaderboard_activity, container, false);
        Bundle args = getArguments();

        return view;
    }

}
