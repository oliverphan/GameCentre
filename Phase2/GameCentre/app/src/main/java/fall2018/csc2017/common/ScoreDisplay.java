package fall2018.csc2017.common;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import fall2018.csc2017.R;
import fall2018.csc2017.scoring.LeaderBoard;
import fall2018.csc2017.scoring.Score;

public interface ScoreDisplay {

    default void displayLeaders(View view, LeaderBoard leaderBoard, String gameName) {
        ArrayList<Score> tempScores = leaderBoard.getTopScores(gameName);
        Class res = R.id.class;
        int numScores = tempScores.size(); //numScores == the number of users too
        try {
            for (int i = 1; i <= numScores; i++) {
                String uri = "user" + String.valueOf(i);
                Field field = res.getField(uri);
                TextView user = view.findViewById(field.getInt(null));
                user.setText(tempScores.get(i - 1).toString());
            }
        } catch (Exception e) {
            Log.e("Drawable Access", "Cannot access the drawable", e);
        }
    }
}
