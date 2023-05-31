package rs.dnikolskaia.model.scorers;

public class UnknownScorer implements ParameterScorer {
    public static final double UNKNOWN_PARAMETER_SCORE = 0.0;

    @Override
    public double getScore() {
        return UNKNOWN_PARAMETER_SCORE;
    }
}
