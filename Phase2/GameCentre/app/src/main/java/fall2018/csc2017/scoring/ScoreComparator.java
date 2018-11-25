package fall2018.csc2017.scoring;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {

    /**
     * Compares its two arguments for order.
     * <p>
     * Returns a negative integer, zero, or a positive integer
     * as s1 is less than, equal to, or greater than s2 in terms
     * of number of score value.
     *
     * @param s1 the first Score to compare
     * @param s2 the second Score to compare
     * @return a negative integer, zero, or a positive integer
     * as r1 is less than, equal to, or greater than r2
     */
    @Override
    public int compare(Score s1, Score s2) {
        return s1.getValue() - s2.getValue();
    }
}