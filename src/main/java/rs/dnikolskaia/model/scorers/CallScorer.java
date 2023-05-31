package rs.dnikolskaia.model.scorers;

public class CallScorer implements ParameterScorer {
    public static final double CALL_PARAMETER_SCORE = 0.0;

    @Override
    public double getScore() {
        return CALL_PARAMETER_SCORE;
    }
}
