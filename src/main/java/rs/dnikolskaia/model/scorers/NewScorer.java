package rs.dnikolskaia.model.scorers;

public class NewScorer implements ParameterScorer {
    public static final double NEW_PARAMETER_SCORE = 0.5;

    @Override
    public double getScore() {
        return NEW_PARAMETER_SCORE;
    }
}
