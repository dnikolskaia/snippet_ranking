package rs.dnikolskaia.model.scorers;

public class ExpressionScorer implements ParameterScorer {
    public static final double STRING_EXPRESSION_PARAMETER_SCORE = 0.5;
    public static final double OTHER_EXPRESSION_PARAMETER_SCORE = 0.0;
    private final String type;

    public ExpressionScorer(String type) {
        this.type = type;
    }

    @Override
    public double getScore() {
        if (type.equals("String"))
            return STRING_EXPRESSION_PARAMETER_SCORE;
        return OTHER_EXPRESSION_PARAMETER_SCORE;
    }
}
