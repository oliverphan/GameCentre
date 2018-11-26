package fall2018.csc2017.common;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.scoring.LeaderBoard;
import fall2018.csc2017.scoring.Score;

public interface ScoreDisplay {

    default void displayLeaders(View view, LeaderBoard leaderBoard, String gameName) {
        ArrayList<Score> tempScores = leaderBoard.getTopScores(gameName);
        ArrayList<Integer> tempScoreValues = new ArrayList<>();
        ArrayList<String> tempScoreUsers = new ArrayList<>();
        int numScores = tempScoreValues.size(); //numScores == the number of users too
        for (Score score : tempScores) {
            tempScoreValues.add(score.getValue());
            tempScoreUsers.add(score.getUsername());
        }
        for (int i = 1; i <= numScores; i++) {
            TextView user = view.findViewById(view.getResources().getIdentifier("user" + i, "id", "res"));
            user.setText(tempScoreUsers.get(i - 1));
            TextView score = view.findViewById(view.getResources().getIdentifier("user" + i, "id", "res"));
            score.setText(tempScoreValues.get(i - 1));
        }
    }
}
