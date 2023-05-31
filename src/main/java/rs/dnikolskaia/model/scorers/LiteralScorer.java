package rs.dnikolskaia.model.scorers;

public class LiteralScorer implements ParameterScorer {
    public static final double LITERAL_PARAMETER_SCORE = 1.0;

    @Override
    public double getScore() {
        return LITERAL_PARAMETER_SCORE;
    }
}
